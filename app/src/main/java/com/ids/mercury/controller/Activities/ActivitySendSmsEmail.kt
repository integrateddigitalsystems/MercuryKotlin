package com.ids.mercury.controller.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_gift_card.btSubmit
import kotlinx.android.synthetic.main.activity_gift_card.etEmail
import kotlinx.android.synthetic.main.activity_gift_card.etPhoneNumber
import kotlinx.android.synthetic.main.activity_send_sms_email.*

import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ActivitySendSmsEmail : AppCompactBase(),RVOnItemClickListener {
    lateinit var  shake: Animation
    var type=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_sms_email)
        init()
        listeners()
    }

    private fun init(){
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

    }

    private fun checkInputs(){
        when {
            etFirstName.text.isNullOrEmpty() -> etFirstName.startAnimation(shake)
            etLastName.text.isNullOrEmpty() -> etLastName.startAnimation(shake)
            type==AppConstants.TYPE_EMAIL && etEmail.text.isNullOrEmpty() -> etEmail.startAnimation(shake)
            type==AppConstants.TYPE_EMAIL && !AppHelper.isValidEmail(etEmail.text.toString()) -> etEmail.startAnimation(shake)
            type==AppConstants.TYPE_SMS && etPhoneNumber.text.isNullOrEmpty() -> linearPhone.startAnimation(shake)
            else -> sendRequestEmail()
        }
    }

    private fun submit(){
        startActivity(Intent(this,ActivitySuccess::class.java))
    }


    override fun onItemClicked(view: View, position: Int) {

    }



    fun sendRequestEmail () {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.sendrequestEmail(MyApplication.memberId.toString(),"2")?.enqueue(object :
                Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    if(response.body()!!.success=="1"){
                        submit()
                    }else
                        toastt(response.body()!!.message!!)

                }
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    toastt(getString(R.string.try_again))
                }
            })
    }

}