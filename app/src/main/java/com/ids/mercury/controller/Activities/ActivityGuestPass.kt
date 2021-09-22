package com.ids.mercury.controller.Activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterCountryCodes

import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.CountryArray
import com.ids.mercury.model.CountryCodes
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*

import kotlinx.android.synthetic.main.activity_guest_passes.*
import kotlinx.android.synthetic.main.activity_guest_passes.etPhoneNumber
import kotlinx.android.synthetic.main.activity_guest_passes.linearPhone
import kotlinx.android.synthetic.main.activity_guest_passes.tvCountryCode
import kotlinx.android.synthetic.main.fragment_signup.*

import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ActivityGuestPass : AppCompactBase() , RVOnItemClickListener {
    lateinit var  shake: Animation
    private var arrayCountries=java.util.ArrayList<CountryCodes>()
    lateinit var adapter : AdapterCountryCodes
    lateinit var dialog :Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_passes)
        init()
        listeners()
    }

    private fun init(){
        shake =  AnimationUtils.loadAnimation(this, R.anim.shake)
        btProfile.show()
        tvToolbarTitle.text=getString(R.string.one_day_guest_pass)
        getGuestPassesCount()
        MyApplication.selectedItemDialog="+961"


    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{
            startActivity(Intent(this,ActivityProfile::class.java))
        }

        tvCountryCode.setOnClickListener{
            showCountires()
        }

        btBuyGuestPass.setOnClickListener{
            startActivity(Intent(this,ActivityBuyExtraGuest::class.java))
        }

        setDatesPicker()
    }

    private fun checkInputs(){
        when {
            etGuestName.text.isNullOrEmpty() -> etGuestName.startAnimation(shake)
            etGuestEmail.text.isNullOrEmpty() -> etGuestEmail.startAnimation(shake)
            !AppHelper.isValidEmail(etGuestEmail.text.toString()) -> etGuestEmail.startAnimation(shake)
            etPhoneNumber.text.isNullOrEmpty() -> linearPhone.startAnimation(shake)
            else -> sendGuestPass()
        }
    }

    private fun submit(){
        startActivity(Intent(this,ActivitySuccess::class.java))
    }


    fun getGuestPassesCount () {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getMemberGuestPasses(MyApplication.memberId.toString())?.enqueue(object :
                Callback<ResponseGuestPasses> {
                override fun onResponse(
                    call: Call<ResponseGuestPasses>,
                    response: Response<ResponseGuestPasses>
                ) {
                    try{
                    if(response.body()!!.success=="1"){
                        btSendGuestPass.isEnabled=true
                        tvGuestPassesCount.text=getString(R.string.guest_passes_available)+" "+response.body()!!.totalPasses!!.toString()
                        btSendGuestPass.setOnClickListener{
                            checkInputs()
                        }
                    }else{
                        btSendGuestPass.isEnabled=false
                        tvGuestPassesCount.text=getString(R.string.guest_passes_available)+" 0"
                    }}catch (e:Exception){
                        btSendGuestPass.isEnabled=false
                        tvGuestPassesCount.text=getString(R.string.guest_passes_available)+" 0"
                    }

                }
                override fun onFailure(call: Call<ResponseGuestPasses>, t: Throwable) {
                    tvGuestPassesCount.text=getString(R.string.guest_passes_available)+" 0"
                }
            })
    }



    private fun setDatesPicker(){

        var textDateListener: DatePickerDialog.OnDateSetListener
        var textDateCalendar: Calendar = Calendar.getInstance()
        var textExpiryCalendar: Calendar = Calendar.getInstance()
        textExpiryCalendar.add(Calendar.DATE,1)
        textDateListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // TODO Auto-generated method stub
                textDateCalendar.set(Calendar.YEAR, year)
                textDateCalendar.set(Calendar.MONTH, monthOfYear)
                textDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                textExpiryCalendar.set(Calendar.YEAR, year)
                textExpiryCalendar.set(Calendar.MONTH, monthOfYear)
                textExpiryCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                textExpiryCalendar.add(Calendar.DATE,1)
                tvIssuedDate.text = AppHelper.dateFormat4.format(textDateCalendar.time)
                tvExpiredDate.text = AppHelper.dateFormat4.format(textExpiryCalendar.time)
            }
        llIssued.setOnClickListener {
           var dateDialog= DatePickerDialog(
                this,
                textDateListener,
                textDateCalendar.get(Calendar.YEAR),
                textDateCalendar.get(Calendar.MONTH),
                textDateCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dateDialog.datePicker.minDate=Date().time
            dateDialog.show()
        }



        tvIssuedDate.text = AppHelper.dateFormat4.format(textDateCalendar.time)
        tvExpiredDate.text = AppHelper.dateFormat4.format(textExpiryCalendar.time)
    }


    fun sendGuestPass () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.sendGuestPass(MyApplication.memberId.toString(),etGuestName.text.toString(),etGuestEmail.text.toString(),
                tvCountryCode.text.toString().replace("+","")+" "+etPhoneNumber.text.toString()
                )?.enqueue(object :
                Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1"){
                        submit()
                    }else
                        toastt(response.body()!!.message!!)

                }
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    loading.hide()
                    toastt(getString(R.string.try_again))
                }
            })
    }


    private fun showCountires(){
        arrayCountries.clear()
        arrayCountries.addAll(Gson().fromJson(loadJSONFromAssets("countries.json"), CountryArray::class.java).countries!!)
        dialog = Dialog(this, R.style.dialogWithoutTitle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.popup_recycler)
        dialog.setCancelable(true)
        val rv: RecyclerView = dialog.findViewById(R.id.rvData)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.layoutManager = layoutManager
        adapter = AdapterCountryCodes(arrayCountries,this)
        rv.adapter = adapter

        try{
            var item=arrayCountries.find { it.code!!.replace("+","").trim()==MyApplication.selectedItemDialog.replace("+","").trim() }
            var position=arrayCountries.indexOf(item!!)
            rv.scrollToPosition(position)}catch (e:Exception){}

        dialog.show()
    }

    override fun onItemClicked(view: View, position: Int) {
        dialog.dismiss()
        MyApplication.selectedItemDialog=adapter.items[position].code!!
        tvCountryCode.text = adapter.items[position].code
    }

}