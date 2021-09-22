package com.ids.mercury.controller.Activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterActivityPackages
import com.ids.mercury.controller.Adapters.AdapterCourts
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.Fragments.*
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.custom.CustomTimePickerDialog
import com.ids.mercury.custom.CustomTimePickerDialog.TIME_PICKER_INTERVAL
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
import java.lang.reflect.Field
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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
    lateinit var calDate: Calendar
    lateinit var calEnd:Calendar
    lateinit var calStart:Calendar

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
            else if(!isVadideDate())
                linearDate.startAnimation(shake)
            else if(!isValidTime()){
                linearStartTime.startAnimation(shake)
                linearEndTime.startAnimation(shake)
            }
           else{
                setDataEdit()
                getCourts()
            }
            }
        }

        linearSelectedData.setOnClickListener{
            linearSelection.show()
            linearData.hide()
        }
    }

    fun getCourts () {
        loading.show()
        var fromDate=selectedDate+" "+tvStartTime.text.toString()
        var toDate=selectedDate+" "+tvEndTime.text.toString()
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
                    try{
                    if(response.body()!!.success=="1" && response.body()!!.courts!!.size>0){
                        arrayCourts.clear()
                        arrayCourts.addAll(response.body()!!.courts!!)
                        setCourts()

                    }}catch (e:java.lang.Exception){}

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
                    try{
                    if(response.body()!!.success=="1" && response.body()!!.activities!!.size>0){
                        arrayActivities.clear()
                        arrayActivities.addAll(response.body()!!.activities!!)
                        linearActivities.setOnClickListener{
                            showActivities()
                        }
                    }}catch (e:Exception){

                    }

                }
                override fun onFailure(call: Call<ResponseActivities>, t: Throwable) {
                }
            })
    }

    private fun showActivities(){
        dialog = Dialog(this, R.style.dialogWithoutTitle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(true)
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
        }else{
            resetAllSelection()
            arrayCourts[position].selected=true
            adapterCourts.notifyDataSetChanged()
        }
    }


   private fun resetAllSelection(){
       for (i in arrayCourts.indices)
           arrayCourts[i].selected=false
   }


    private fun setDatesPicker(){

        var textDateListener: DatePickerDialog.OnDateSetListener
        calDate = Calendar.getInstance()
        textDateListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // TODO Auto-generated method stub
                calDate.set(Calendar.YEAR, year)
                calDate.set(Calendar.MONTH, monthOfYear)
                calDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                tvSelectedDate.text = AppHelper.dateFormat4.format(calDate.time)
                selectedDate=AppHelper.dateFormat11.format(calDate.time)

            }
        linearDate.setOnClickListener {
            DatePickerDialog(
                this,
                textDateListener,
                calDate.get(Calendar.YEAR),
                calDate.get(Calendar.MONTH),
                calDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        tvSelectedDate.text=AppHelper.dateFormat4.format(calDate.time)
        selectedDate=AppHelper.dateFormat11.format(calDate.time)
    }


    private fun setStartTimePicker(){

       calStart = Calendar.getInstance()
        calStart.add(Calendar.HOUR_OF_DAY,1)
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calStart.set(Calendar.HOUR_OF_DAY, hour)
            calStart.set(Calendar.MINUTE, minute)
            if(minute % TIME_PICKER_INTERVAL != 0)
               calStart.set(Calendar.MINUTE, minute+ TIME_PICKER_INTERVAL-minute % TIME_PICKER_INTERVAL)
            tvStartTime.text = AppHelper.dateFormat8.format(calStart.time)
        }
        var timePickerStart = CustomTimePickerDialog(this, timeSetListener, calStart.get(Calendar.HOUR_OF_DAY), calStart.get(Calendar.MINUTE), false)
        linearStartTime.setOnClickListener{
            timePickerStart.show()
        }

        tvStartTime.text = AppHelper.dateFormat9.format(calStart.time)

    }

    private fun setEndTimePicker(){

        calEnd = Calendar.getInstance()
        calEnd.add(Calendar.HOUR_OF_DAY,2)

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calEnd.set(Calendar.HOUR_OF_DAY, hour)
            calEnd.set(Calendar.MINUTE, minute)
            if(minute % TIME_PICKER_INTERVAL != 0)
                calEnd.set(Calendar.MINUTE, minute+ TIME_PICKER_INTERVAL-minute % TIME_PICKER_INTERVAL)
            tvEndTime.text = AppHelper.dateFormat8.format(calEnd.time)
        }

        var timePickerEnd=CustomTimePickerDialog(this, timeSetListener, calEnd.get(Calendar.HOUR_OF_DAY), calEnd.get(Calendar.MINUTE), false)
        linearEndTime.setOnClickListener{
            timePickerEnd.show()
        }

        tvEndTime.text = AppHelper.dateFormat9.format(calEnd.time)

    }

    private fun setDataEdit(){
        tvSelectedDateToEdit.text=tvSelectedDate.text.toString()
        tvStartTimeToEdit.text=tvStartTime.text.toString()
        tvEndTimeToEdit.text=tvEndTime.text.toString()
        tvSelectedActivityToEdit.text=tvSelectedActivity.text.toString()
    }


    private fun isVadideDate():Boolean{
      var valid=true
      if(  AppHelper.dateFormat4.format(calDate.time).compareTo( AppHelper.dateFormat4.format(Calendar.getInstance().time))<0)
         valid=false
        return valid
    }


    private fun isValidTime():Boolean{

        var valid=true
        var startTime=tvStartTime.text.toString()
        var endTime=tvEndTime.text.toString()
        var startFullTime=AppHelper.dateFormat4.format(calDate.time)+" "+ startTime
        var endFullTime=AppHelper.dateFormat4.format(calDate.time)+" "+ endTime
         var  cal = Calendar.getInstance()

        //check if start time less than end time
        if(AppHelper.dateFormat10.parse(endFullTime).compareTo(AppHelper.dateFormat10.parse(startFullTime)) < 0)
            valid=false
        else if(AppHelper.dateFormat10.parse(startFullTime).compareTo(AppHelper.dateFormat10.parse(AppHelper.dateFormat10.format(cal.time))) < 0)
            valid=false
        //check if start time is betweem 12 AM and 6:00 AM
        else if(AppHelper.dateFormat8.parse(startTime)  > AppHelper.dateFormat8.parse("12:00 AM") && AppHelper.dateFormat8.parse(startTime) < AppHelper.dateFormat8.parse("06:00 AM"))
            valid=false
        //check if end time is betweem 12:00 AM and 6:00 :AM
        else if(AppHelper.dateFormat8.parse(endTime)  > AppHelper.dateFormat8.parse("12:00 AM") && AppHelper.dateFormat8.parse(endTime) < AppHelper.dateFormat8.parse("06:00 AM"))
            valid=false
        else if(((AppHelper.dateFormat8.parse(endTime).time  - AppHelper.dateFormat8.parse(startTime) .time)/(1000*60)) < 60 )
            valid=false
        return valid
    }

}
