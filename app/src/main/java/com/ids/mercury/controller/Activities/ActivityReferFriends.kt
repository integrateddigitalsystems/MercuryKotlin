package com.ids.mercury.controller.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.nedim.maildroidx.MaildroidX
import co.nedim.maildroidx.MaildroidXType
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterFriends
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.Friends
import com.ids.mercury.model.response.ResponseFriends
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_refer_friend.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ActivityReferFriends : AppCompactBase(),RVOnItemClickListener {
    private var arrayFriends=java.util.ArrayList<Friends>()
    lateinit var adapter : AdapterFriends
    lateinit var  shake: Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refer_friend)
        init()
        listeners()
    }

    private fun init(){
        shake =  AnimationUtils.loadAnimation(this, R.anim.shake)
        btProfile.show()
        tvToolbarTitle.text=getString(R.string.invite_n_a_friend)
        getFriends()


    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{
            startActivity(Intent(this,ActivityProfile::class.java))
        }

        btEmail.setOnClickListener{
            //startActivity(Intent(this,ActivitySendSmsEmail::class.java).putExtra("type",AppConstants.TYPE_EMAIL))
            sendAppEmail()
        }

        btSms.setOnClickListener{
            startActivity(Intent(this,ActivitySendSmsEmail::class.java).putExtra("type",AppConstants.TYPE_SMS))

        }

    }

    private fun sendAppEmail(){
/*        toastt("sending email...")
        val sender = GMailSender(
            "feedback@mercurybeirut.com",
            "admin@123"
        )
        Thread {
            try {
                sender.sendMail("hello", "test body", "ibrahim.h.ahmd@gmail.com", "i.haydar@ids.com.lb")
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }.start()*/

        MaildroidX.Builder()
            .smtp("smtp.gmail.com")
            .smtpUsername("bousouhou@gmail.com")
            .smtpPassword("acerpcPC1234")
            .port("465")
            .isStartTLSEnabled(true)
            .type(MaildroidXType.HTML)
            .to("i.haydar@ids.com.lb")
            .from("bousouhou@gmail.com")
            .subject("hello test")
            .body("test body1")
            .isJavascriptDisabled(false)
            .onCompleteCallback(object : MaildroidX.onCompleteCallback{
                override val timeout: Long = 30000
                override fun onSuccess() {
                    Log.d("MaildroidX",  "SUCCESS")
                }
                override fun onFail(errorMessage: String) {
                    Log.d("MaildroidX",  "FAIL")
                }
            })
            .mail()
     }


    override fun onItemClicked(view: View, position: Int) {

    }


    fun getFriends () {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getReferrals(MyApplication.memberId.toString())?.enqueue(object :
                Callback<ResponseFriends> {
                override fun onResponse(
                    call: Call<ResponseFriends>,
                    response: Response<ResponseFriends>
                ) {
                    if(response.body()!!.success=="1" && response.body()!!.referrals!!.size>0){
                        setData(response.body()!!.referrals)
                    }


                }
                override fun onFailure(call: Call<ResponseFriends>, t: Throwable) {
                }
            })
    }

    private fun setData(friends: ArrayList<Friends>?) {
        arrayFriends.clear()
        arrayFriends.addAll(friends!!)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvFriends.layoutManager = layoutManager
        adapter = AdapterFriends(arrayFriends,this)
        rvFriends.adapter = adapter

    }




}