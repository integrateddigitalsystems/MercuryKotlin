package com.ids.mercury.controller.Fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ids.mercury.R
import com.ids.mercury.controller.Activities.ActivityLogin
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.ResponseMessage
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_verification.*
import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentVerification : Fragment() , RVOnItemClickListener {

    var canClick=true
    var canResend=false
    lateinit var timer:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_verification, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listeners()


    }

    private fun init(){
        sendSms()
    }

    private fun listeners(){
        btBack.setOnClickListener{
            cancelTimer()
            (activity as ActivityLogin).backClick()
        }

        btResend.setOnClickListener{
            if(canResend)
                sendSms()
        }

        pvCode.setAnimationEnable(true)
        pvCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 4) {
                    checkVerification(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }

    private fun cancelTimer(){
        try{timer.cancel()}catch (e:Exception){}
    }

    override fun onResume() {
        super.onResume()
        canClick=true
    }

    override fun onItemClicked(view: View, position: Int) {

    }

    override fun onDestroy() {
        cancelTimer()
        super.onDestroy()
    }
    private fun startTimer(){
        try{
         timer = object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.show()
                var remainigSecond=millisUntilFinished/1000
                var remaingStringTime=remainigSecond.toString()
                if(remainigSecond.toString().length==1)
                    remaingStringTime="0"+remainigSecond
                tvTimer.text="00:"+remaingStringTime
                canResend=false
            }

            override fun onFinish() {
              canResend=true
              tvTimer.hide()
            }
        }
        timer.start()}catch (e:java.lang.Exception){}
    }

    private fun sendSms(){
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.sendVerificationSms(MyApplication.tempMemberId)?.enqueue(object :
                Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1"){
                        startTimer()
                    }else
                        toastt(getString(R.string.try_again))

                }
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    loading.hide()
                    toastt(getString(R.string.try_again))
                }
            })
    }


    private fun checkVerification(text:String){
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.checkVerification(MyApplication.tempMemberId,text)?.enqueue(object :
                Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1"){
                        hideKeyboard()
                        cancelTimer()
                        (activity as ActivityLogin).goToSetPassword()
                    }else
                        toastt(response.body()!!.message!!)

                }
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    loading.hide()
                    toastt(getString(R.string.try_again))
                }
            })
    }



}









