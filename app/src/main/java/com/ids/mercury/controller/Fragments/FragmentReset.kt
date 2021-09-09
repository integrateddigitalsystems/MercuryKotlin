package com.ids.mercury.controller.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils

import androidx.fragment.app.Fragment
import com.ids.mercury.R
import com.ids.mercury.controller.Activities.ActivityLogin

import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_forget.*
import kotlinx.android.synthetic.main.fragment_forget.etUsername
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.toolbar.*


class FragmentReset : Fragment() , RVOnItemClickListener {

    var canClick=true
    lateinit var  shake: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.mercury.R.layout.fragment_forget, container, false)


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

        btReset.setOnClickListener{
            resetPassword()
        }
    }

    private fun resetPassword(){
        if(etUsername.text.isNullOrEmpty())
            etUsername.startAnimation(shake)
        else
            reset()

    }


    private fun reset(){
       hideKeyboard()
       MyApplication.tempMemberId=etUsername.text.toString()
       MyApplication.verificationTitle=getString(R.string.forgot_password)
       (activity as ActivityLogin).goToVerification()
    }


    override fun onResume() {
        super.onResume()
        canClick=true
    }

    override fun onItemClicked(view: View, position: Int) {

    }


}