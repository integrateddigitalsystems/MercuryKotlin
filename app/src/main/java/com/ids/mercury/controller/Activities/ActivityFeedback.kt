package com.ids.mercury.controller.Activities

import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.activity_send_sms_email.tvPageTitle

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import co.nedim.maildroidx.MaildroidX
import co.nedim.maildroidx.MaildroidXType
import com.ids.mercury.R
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_gift_card.btSubmit
import kotlinx.android.synthetic.main.loading_trans.*

import kotlinx.android.synthetic.main.toolbar.*




class ActivityFeedback : AppCompactBase() {
    lateinit var  shake: Animation

    var messageBody=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        init()
        listeners()
    }

    private fun init(){
        shake =  AnimationUtils.loadAnimation(this, R.anim.shake)
        btProfile.show()
        //tvPageTitle.text=getString(R.string.feedback)
        tvToolbarTitle.text=getString(R.string.feedback)
    }

    private fun listeners(){
        btBack.setOnClickListener{
            hideKeyboard()
            super.onBackPressed()}
        btProfile.setOnClickListener{
            startActivity(Intent(this,ActivityProfile::class.java))
        }
        btSubmit.setOnClickListener{
            checkInputs()
        }


    }

    private fun checkInputs(){
        when {
            etFeedback.text.isNullOrEmpty() -> etFeedback.startAnimation(shake)
            else -> {
                sendAppEmail()
            }
        }
    }




    private fun sendAppEmail(){

        try{
            loading.show()
            messageBody = etFeedback.text.toString()

            MaildroidX.Builder()
                .smtp("smtp.gmail.com")
                .smtpUsername(MyApplication.sEmail)
                .smtpPassword(MyApplication.sPassword)
                .port("465")
                .isStartTLSEnabled(true)
                .type(MaildroidXType.HTML)
                 .to(MyApplication.rEmail)
                //.to("i.haydar@ids.com.lb")
                .from(MyApplication.sEmail)
                .subject(MyApplication.emailSubject)
                .body(messageBody)
                .isJavascriptDisabled(false)

                .onCompleteCallback(object : MaildroidX.onCompleteCallback{
                    override val timeout: Long = 30000
                    override fun onSuccess() {
                        loading.hide()
                        toastt(getString(R.string.email_sent))
                        submit()
                    }
                    override fun onFail(errorMessage: String) {
                        loading.hide()
                        toastt(errorMessage)
                    }
                })
                .mail()
        }catch (e:Exception){
            loading.hide()
            toastt("error")
        }
    }

    private fun submit(){
        startActivity(Intent(this,ActivitySuccess::class.java))
    }

}