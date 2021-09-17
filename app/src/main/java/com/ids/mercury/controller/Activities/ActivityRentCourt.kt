package com.ids.mercury.controller.Activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterActivityPackages
import com.ids.mercury.controller.Adapters.AdapterCourts
import com.ids.mercury.controller.Adapters.AdapterGymPackages
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.Fragments.*
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_academy.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_rent_court.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class ActivityRentCourt : AppCompactBase(),RVOnItemClickListener {
    private lateinit var fragmentManager: FragmentManager
    private var arrayCourts=java.util.ArrayList<Court>()
    private var arrayActivities=java.util.ArrayList<Activity>()
    lateinit var adapterActivityPackages: AdapterActivityPackages
    lateinit var adapterCourts : AdapterCourts
    lateinit var dialog :Dialog
    lateinit var  shake: Animation
    var selectedDate=""
    var selectedActivity=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_court)
        init()
        listeners()
    }


    private fun init(){
        shake =  AnimationUtils.loadAnimation(this, R.anim.shake)
        tvToolbarTitle.text=getString(R.string.rent_a_court)
        MyApplication.selectedActivityId=0
        getActivities()

    }

    private fun listeners(){
        setDatesPicker()
        setStartTimePicker()
        setEndTimePicker()
        btBack.setOnClickListener{
            super.onBackPressed()
        }

        btProceed.setOnClickListener{
            if(linearSelection.isVisible){
            if(MyApplication.selectedActivityId==0)
                linearActivities.startAnimation(shake)
            else{

                setDataEdit()
                getCourts()
            }}
        }

        linearSelectedData.setOnClickListener{
            linearSelection.show()
            linearData.hide()
        }
    }

    fun getCourts () {
        loading.show()
        var fromDate="09/17/2021 2:00 PM"
        var toDate="09/17/2021 3:00 PM"
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.checkAvailability(MyApplication.selectedActivityId,fromDate,toDate)?.enqueue(object :
                Callback<ResponseCourts> {
                override fun onResponse(
                    call: Call<ResponseCourts>,
                    response: Response<ResponseCourts>
                ) {
                    loading.hide()
                    linearSelection.hide()
                    linearData.show()
                    if(response.body()!!.success=="1" && response.body()!!.courts!!.size>0){
                        arrayCourts.clear()
                        arrayCourts.addAll(response.body()!!.courts!!)
                        setCourts()

                    }

                }
                override fun onFailure(call: Call<ResponseCourts>, t: Throwable) {
                    loading.hide()
                    linearSelection.hide()
                    linearData.show()
                }
            })
    }


    private fun setCourts(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvCourts.layoutManager = layoutManager
        adapterCourts = AdapterCourts(arrayCourts,this)
        rvCourts.adapter = adapterCourts
    }





    fun getActivities () {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getActivities()?.enqueue(object :
                Callback<ResponseActivities> {
                override fun onResponse(
                    call: Call<ResponseActivities>,
                    response: Response<ResponseActivities>
                ) {
                    if(response.body()!!.success=="1" && response.body()!!.activities!!.size>0){
                        arrayActivities.clear()
                        arrayActivities.addAll(response.body()!!.activities!!)
                        linearActivities.setOnClickListener{
                            showActivities()
                        }
                    }

                }
                override fun onFailure(call: Call<ResponseActivities>, t: Throwable) {
                }
            })
    }

    private fun showActivities(){
        dialog = Dialog(this, R.style.dialogWithoutTitle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.popup_recycler)
        dialog.setCancelable(true)
        val rv: RecyclerView = dialog.findViewById(R.id.rvData)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.layoutManager = layoutManager
        adapterActivityPackages = AdapterActivityPackages(arrayActivities,this)
        rv.adapter = adapterActivityPackages
        try{
            var item=arrayActivities.find { it.id!! == MyApplication.selectedActivityId }
            var position=arrayActivities.indexOf(item!!)
            rv.scrollToPosition(position)}catch (e:Exception){}
        dialog.show()
    }

    override fun onItemClicked(view: View, position: Int) {
        if(view.id==R.id.linearPopup){
         MyApplication.selectedActivityId=adapterActivityPackages.items[position].id!!
         tvSelectedActivity.text=adapterActivityPackages.items[position].name!!
         dialog.dismiss()
        }
    }





    private fun setDatesPicker(){

        var textDateListener: DatePickerDialog.OnDateSetListener
        var textDateCalendar: Calendar = Calendar.getInstance()
        textDateListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // TODO Auto-generated method stub
                textDateCalendar.set(Calendar.YEAR, year)
                textDateCalendar.set(Calendar.MONTH, monthOfYear)
                textDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                tvSelectedDate.text = AppHelper.dateFormat4.format(textDateCalendar.time)
                selectedDate=AppHelper.dateFormat3.format(textDateCalendar.time)

            }
        linearDate.setOnClickListener {
            DatePickerDialog(
                this,
                textDateListener,
                textDateCalendar.get(Calendar.YEAR),
                textDateCalendar.get(Calendar.MONTH),
                textDateCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        tvSelectedDate.text=AppHelper.dateFormat4.format(textDateCalendar.time)
    }


    private fun setStartTimePicker(){

        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            tvStartTime.text = AppHelper.dateFormat8.format(cal.time)
        }
        var timePickerStart = TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false)
        linearStartTime.setOnClickListener{
            timePickerStart.show()
        }
        tvStartTime.text = AppHelper.dateFormat8.format(cal.time)
        AppHelper.setTimePickerInterval(timePickerStart)
    }

    private fun setEndTimePicker(){

        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            tvEndTime.text = AppHelper.dateFormat8.format(cal.time)
        }
        linearEndTime.setOnClickListener{
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
        }

    }

    private fun setDataEdit(){
        tvSelectedDateToEdit.text=tvSelectedDate.text.toString()
        tvStartTimeToEdit.text=tvStartTime.text.toString()
        tvEndTimeToEdit.text=tvEndTime.text.toString()
        tvSelectedActivityToEdit.text=tvSelectedActivity.text.toString()
    }

}
