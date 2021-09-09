package com.ids.mercury.controller.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils

import androidx.fragment.app.Fragment
import com.ids.mercury.R
import com.ids.mercury.controller.Activities.ActivityHome
import com.ids.mercury.controller.Activities.ActivityLogin

import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.ResponseMessage
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.fragment_login.*

import kotlinx.android.synthetic.main.loading_trans.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentLogin : Fragment() , RVOnItemClickListener {

    var canClick=true
    lateinit var  shake: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_login, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listeners()

    }

    private fun init(){
        shake =  AnimationUtils.loadAnimation(requireActivity(), R.anim.shake)
    }

    private fun listeners(){
        btLogin.setOnClickListener{
            checkLogin()
        }

        btSignUp.setOnClickListener{
            (activity as ActivityLogin).goToSignUp()
        }

        btForgetPassword.setOnClickListener{
            (activity as ActivityLogin).goToForgotPassword()
        }
    }

    private fun checkLogin(){
        when {
            etUsername.text.isNullOrEmpty() -> etUsername.startAnimation(shake)
            etPassword.text.isNullOrEmpty() -> etPassword.startAnimation(shake)
            else -> {
                login()
            }
        }
    }

    fun login () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.login(etUsername.text.toString(), etPassword.text.toString())?.enqueue(object : Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1"){
                         MyApplication.memberId=response.body()!!.memberId!!
                         MyApplication.isLoggedIn=true
                         MyApplication.cnm=etUsername.text.toString()
                         MyApplication.cps=etPassword.text.toString()
                         goHome()
                    }

                }
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    loading.hide()
                }
            })
    }


    override fun onResume() {
        super.onResume()
        canClick=true
    }

    private fun goHome(){
        val mIntent = Intent(requireActivity(), ActivityHome::class.java)
        requireActivity().finishAffinity()
        startActivity(mIntent)
    }


    override fun onItemClicked(view: View, position: Int) {

    }


}

















