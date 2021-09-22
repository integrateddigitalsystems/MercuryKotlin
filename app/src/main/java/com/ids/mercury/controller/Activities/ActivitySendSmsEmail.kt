package com.ids.mercury.controller.Activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_gift_card.btSubmit
import kotlinx.android.synthetic.main.activity_gift_card.etEmail
import kotlinx.android.synthetic.main.activity_gift_card.etPhoneNumber
import kotlinx.android.synthetic.main.activity_guest_passes.*
import kotlinx.android.synthetic.main.activity_send_sms_email.*
import kotlinx.android.synthetic.main.activity_send_sms_email.linearPhone
import kotlinx.android.synthetic.main.activity_send_sms_email.tvCountryCode
import kotlinx.android.synthetic.main.loading_trans.*

import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ActivitySendSmsEmail : AppCompactBase(),RVOnItemClickListener {
    lateinit var  shake: Animation
    var type=0
    private var arrayCountries=java.util.ArrayList<CountryCodes>()
    lateinit var adapter : AdapterCountryCodes
    lateinit var dialog :Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_sms_email)
        init()
        listeners()
    }

    private fun init(){
        MyApplication.selectedItemDialog="+961"
        type=intent.getIntExtra("type",0)
        shake =  AnimationUtils.loadAnimation(this, R.anim.shake)
        btProfile.show()
        if(type == AppConstants.TYPE_EMAIL){
            etEmail.show()
            linearPhone.hide()

        }else{
            etEmail.hide()
            linearPhone.show()
        }
        tvPageTitle.text=getString(R.string.please_enter_your_friend_information)

    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{
            startActivity(Intent(this,ActivityProfile::class.java))
        }
        btSubmit.setOnClickListener{
            checkInputs()
        }

        tvCountryCode.setOnClickListener{
            showCountires()
        }


    }

    private fun checkInputs(){
        when {
            etFirstName.text.isNullOrEmpty() -> etFirstName.startAnimation(shake)
            etLastName.text.isNullOrEmpty() -> etLastName.startAnimation(shake)
            type==AppConstants.TYPE_EMAIL && etEmail.text.isNullOrEmpty() -> etEmail.startAnimation(shake)
            type==AppConstants.TYPE_EMAIL && !AppHelper.isValidEmail(etEmail.text.toString()) -> etEmail.startAnimation(shake)
            type==AppConstants.TYPE_SMS && etPhoneNumber.text.isNullOrEmpty() -> linearPhone.startAnimation(shake)
            else -> inviteFriend()
        }
    }

    private fun submit(){
        startActivity(Intent(this,ActivitySuccess::class.java))
    }


    override fun onItemClicked(view: View, position: Int) {
        dialog.dismiss()
        MyApplication.selectedItemDialog=adapter.items[position].code!!
        tvCountryCode.text = adapter.items[position].code
    }



    fun inviteFriend () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.inviteFriend(
                MyApplication.memberId.toString(),
                type.toString(),
                if(type == AppConstants.TYPE_EMAIL) etEmail.text.toString() else "",
                if(type == AppConstants.TYPE_SMS) tvCountryCode.text.toString().replace("+","")+" "+etPhoneNumber.text.toString() else "",
                etFirstName.text.toString(),
                etLastName.text.toString()
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

}