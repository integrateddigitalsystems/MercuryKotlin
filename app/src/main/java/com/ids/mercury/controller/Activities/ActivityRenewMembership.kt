package com.ids.mercury.controller.Activities


import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterCoachesPT
import com.ids.mercury.controller.Adapters.AdapterCountryCodes
import com.ids.mercury.controller.Adapters.AdapterGymPackages
import com.ids.mercury.controller.Adapters.AdapterPTPackages
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.CountryCodes
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_renew_membership.*


import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ActivityRenewMembership : AppCompactBase(),RVOnItemClickListener ,PaymentListener {
    private var arrayData=java.util.ArrayList<MemberShip>()
    lateinit var adapter : AdapterCountryCodes
    var type=0
    var selectedCoachId: Int? = -1
    private var arrayGymPackages=java.util.ArrayList<GymPackage>()
    lateinit var adapterGymPackages : AdapterGymPackages
    var adapterCoach : AdapterCoachesPT?=null
    var coaches: ArrayList<Coach> = arrayListOf()

    private var arrayPTPackages=java.util.ArrayList<PtPackage>()
    lateinit var adapterPTPackages : AdapterPTPackages

    lateinit var dialog :Dialog
    lateinit var  shake: Animation
    var selectedDate=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renew_membership)
        init()
        listeners()

    }


    private fun init(){
        shake =  AnimationUtils.loadAnimation(this, R.anim.shake)
        type=intent.getIntExtra("type",0)
        if(type==AppConstants.TYPE_GYM){
            tvToolbarTitle.text=getString(R.string.renew_membership)
            getGymPackages()
            llSelectCoach.hide()
        }
        else{
            tvToolbarTitle.text=getString(R.string.renew_pt)
            getPTPackages()
            getCoaches()
            llSelectCoach.show()
        }
        MyApplication.selectedGymPackageId=0
        MyApplication.selectedPtPackageId=0



    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{startActivity(Intent(this,ActivityProfile::class.java))}
        setDatesPicker()
        btProceed.setOnClickListener{
            if(tvSelectedPackage.text == getString(R.string.choose_package))
                linearPackages.startAnimation(shake)
             else if(selectedDate.isEmpty())
                linearDate.startAnimation(shake)
            else if (selectedCoachId==-1 && type != AppConstants.TYPE_GYM){
                AppHelper.createDialog(this,getString(R.string.please_select_coach))
            } else if (tvAmount.text.toString().isNotEmpty()) {
                PaymentApiDialog.showPaymentDialog(
                    this,
                    this,
                    tvAmount.text.toString().lowercase().replace("usd", "").replace(" ", "")
                )
            }

        }
    }


    override fun onItemClicked(view: View, position: Int) {
        if(view.id == R.id.main_body){
            coaches.forEach {
                it.selected = false
            }
            coaches.get(position).selected = true
            selectedCoachId = coaches.get(position).id
            adapterCoach!!.notifyDataSetChanged()
        }else {
            if (type == AppConstants.TYPE_GYM) {
                MyApplication.selectedGymPackageId = adapterGymPackages.items[position].id!!
                tvSelectedPackage.text = adapterGymPackages.items[position].name!!
                if (selectedDate.isNotEmpty())
                    getAmount(selectedDate, MyApplication.selectedGymPackageId)
            } else {
                MyApplication.selectedPtPackageId = adapterPTPackages.items[position].id!!
                tvSelectedPackage.text = adapterPTPackages.items[position].name!!
                tvAmount.show()
                tvAmount.text = adapterPTPackages.items[position].amount!!.toString()
            }
            dialog.dismiss()
        }
    }

    fun setUpCoachData(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_available_coaches.layoutManager = layoutManager
        adapterCoach = AdapterCoachesPT(coaches, this)
        recycler_available_coaches.adapter = adapterCoach
    }

    fun getCoaches() {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getCoaches(1)?.enqueue(object :
                Callback<ResponseCoaches> {
                override fun onResponse(
                    call: Call<ResponseCoaches>,
                    response: Response<ResponseCoaches>
                ) {

                    if (response.body()!!.success == "1") {
                        coaches.clear()
                        coaches.addAll(response.body()!!.coaches!!)
                        setUpCoachData()
                    }

                }

                override fun onFailure(call: Call<ResponseCoaches>, t: Throwable) {

                }
            })
    }

    fun getGymPackages () {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getGymPackages()?.enqueue(object :
                Callback<ResponseGymPackages> {
                override fun onResponse(
                    call: Call<ResponseGymPackages>,
                    response: Response<ResponseGymPackages>
                ) {

                    if(response.body()!!.success=="1" && response.body()!!.gymPackages!!.size>0){
                         arrayGymPackages.addAll(response.body()!!.gymPackages!!)
                         linearPackages.setOnClickListener{showGymPackages()}
                    }

                }
                override fun onFailure(call: Call<ResponseGymPackages>, t: Throwable) {

                }
            })
    }


    fun getPTPackages () {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getPTPackages()?.enqueue(object :
                Callback<ResponsePtPackages> {
                override fun onResponse(
                    call: Call<ResponsePtPackages>,
                    response: Response<ResponsePtPackages>
                ) {

                    if(response.body()!!.success=="1" && response.body()!!.ptPackages!!.size>0){
                        arrayPTPackages.addAll(response.body()!!.ptPackages!!)
                        linearPackages.setOnClickListener{showPTPackages()}
                    }

                }
                override fun onFailure(call: Call<ResponsePtPackages>, t: Throwable) {
                }
            })
    }


    fun getAmount (date:String,packageId:Int) {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getAmount(packageId,date)?.enqueue(object :
                Callback<ResponseAmount> {
                override fun onResponse(
                    call: Call<ResponseAmount>,
                    response: Response<ResponseAmount>
                ) {

                    if(response.body()!!.success=="1"){
                        tvAmount.show()
                        tvAmount.text=response.body()!!.amount!!.amount!!.toString()+" "+response.body()!!.amount!!.symbol!!
                    }
                }
                override fun onFailure(call: Call<ResponseAmount>, t: Throwable) {
                }
            })
    }




    private fun showGymPackages(){
        dialog = Dialog(this, R.style.dialogWithoutTitle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.popup_recycler)
        dialog.setCancelable(true)
        val rv: RecyclerView = dialog.findViewById(R.id.rvData)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.layoutManager = layoutManager
        adapterGymPackages = AdapterGymPackages(arrayGymPackages,this)
        rv.adapter = adapterGymPackages
        try{
            var item=arrayGymPackages.find { it.id!! == MyApplication.selectedGymPackageId }
            var position=arrayGymPackages.indexOf(item!!)
            rv.scrollToPosition(position)}catch (e:Exception){}
        dialog.show()
    }


    private fun showPTPackages(){
        dialog = Dialog(this, R.style.dialogWithoutTitle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      //  dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.popup_recycler)
        dialog.setCancelable(true)
        val rv: RecyclerView = dialog.findViewById(R.id.rvData)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.layoutManager = layoutManager
        adapterPTPackages = AdapterPTPackages(arrayPTPackages,this)
        rv.adapter = adapterPTPackages
        try{
            var item=arrayPTPackages.find { it.id!! == MyApplication.selectedPtPackageId }
            var position=arrayPTPackages.indexOf(item!!)
            rv.scrollToPosition(position)}catch (e:Exception){}
        dialog.show()
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
                tvStartDate.text = AppHelper.dateFormat3.format(textDateCalendar.time)
                selectedDate=AppHelper.dateFormat2.format(textDateCalendar.time)
                if(type == AppConstants.TYPE_GYM && tvSelectedPackage.text != getString(R.string.choose_package))
                  getAmount(selectedDate,MyApplication.selectedGymPackageId)

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
    }
    fun saveGymMembership(){
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.saveGymMembership(
                MyApplication.memberId,
                MyApplication.selectedGymPackageId,
                selectedDate,
                0,
                tvAmount.text.toString().lowercase().replace("usd", "").replace(" ", "").toDouble(),
                MyApplication.lastUsedOrderId.toString() ,
                0
            )?.enqueue(object :
                Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    if(response.body()!!.success == "1")
                        success()
                    else
                        logw("renewmember","not working")

                }

                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    logw("renewmember","not working")
                }
            })
    }
    fun saveGymPt(){
        var x = tvAmount.text.toString().lowercase().replace("usd", "").replace(" ", "")
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.saveGymPt(
                MyApplication.memberId,
                MyApplication.selectedPtPackageId,
                selectedCoachId!!,
                selectedDate,
                "0",
                tvAmount.text.toString().lowercase().replace("usd", "").replace(" ", ""),
                MyApplication.lastUsedOrderId.toString()
            )?.enqueue(object :
                Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    if(response.body()!!.success == "1")
                        success()
                    else
                    {
                        logw("renewmember","not working")
                    }


                }

                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    logw("renewmember","not working")
                }
            })
    }

    fun success(){
        startActivity(Intent(this, ActivitySuccess::class.java))
    }
    override fun onFinishPayment(success: Boolean) {
        if (success) {
            if (type == AppConstants.TYPE_GYM) {
                saveGymMembership()
            }else{
                saveGymPt()
            }

        }else{
            AppHelper.createDialog(this,getString(R.string.payment_error))
        }
    }

}