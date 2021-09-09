package com.ids.mercury.controller.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.ids.mercury.R
import com.ids.mercury.controller.Activities.ActivityLogin
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.ResponseMessage
import com.ids.mercury.utils.RetrofitClient
import com.ids.mercury.utils.RetrofitInterface
import com.ids.mercury.utils.hide
import com.ids.mercury.utils.show
import kotlinx.android.synthetic.main.fragment_forget.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_set_password.*
import kotlinx.android.synthetic.main.fragment_set_password.etPassword
import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentSetPassword : Fragment() , RVOnItemClickListener {

    var canClick=true
    lateinit var  shake: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.mercury.R.layout.fragment_set_password, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listeners()

    }



    private fun init(){
        shake =  AnimationUtils.loadAnimation(requireActivity(), R.anim.shake)
    }

    private fun listeners(){
        btBack.setOnClickListener{
            (activity as ActivityLogin).backClick()
        }

        btSubmit.setOnClickListener{
          checkAndSet()
        }
    }

    private fun checkAndSet(){
        when {
            etPassword.text.isNullOrEmpty() -> etPassword.startAnimation(shake)
            etConfirmPassword.text.isNullOrEmpty() -> etConfirmPassword.startAnimation(shake)
            etPassword.text.toString().trim()!=etConfirmPassword.text.toString().trim() -> {
                etPassword.startAnimation(shake)
                etConfirmPassword.startAnimation(shake)
            }
            else -> resetPassword()
        }
    }

    override fun onResume() {
        super.onResume()
        canClick=true
    }

    override fun onItemClicked(view: View, position: Int) {

    }

    fun resetPassword () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.resetPassword(MyApplication.tempMemberId,etPassword.text.toString())?.enqueue(object :
                Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1"){
                        MyApplication.selectedFragment=Fragment()
                        MyApplication.selectedFragmentTag=""
                        val mIntent = Intent(requireActivity(), ActivityLogin::class.java)
                        activity!!.finishAffinity()
                        startActivity(mIntent)
                    }

                }
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    loading.hide()
                }
            })
    }

}







