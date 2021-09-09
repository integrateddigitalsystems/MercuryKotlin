package com.ids.mercury.controller.Activities


import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils

import androidx.fragment.app.FragmentManager
import com.ids.mercury.R
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.Fragments.*
import com.ids.mercury.utils.AppConstants
import com.ids.mercury.utils.addFragment
import com.ids.mercury.utils.replaceFragment
import kotlinx.android.synthetic.main.fragment_login.*


class ActivityLogin : AppCompactBase() {
    private lateinit var fragmentManager: FragmentManager
    var fragmentAvailable= AppConstants.LOGIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        defaultFragment()

    }


    private fun init(){
        fragmentManager = supportFragmentManager
    }


    fun defaultFragment(){
       replaceFragment(R.id.container,fragmentManager,FragmentLogin(), AppConstants.LOGIN_FRAG)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun goToSignUp(){
        addFragment(R.id.container,fragmentManager, FragmentSignUp(), AppConstants.SIGNUP_FRAG)
    }

    fun goToForgotPassword(){
        addFragment(R.id.container,fragmentManager,FragmentReset(), AppConstants.FORGET_FRAG)
    }

    fun goToVerification(){
        addFragment(R.id.container,fragmentManager,FragmentVerification(), AppConstants.VERIFICATION_FRAG)
    }

    fun goToSetPassword(){
        addFragment(R.id.container,fragmentManager,FragmentSetPassword(), AppConstants.SET_PASSWORD_FRAG)
    }
    fun backClick(){
        super.onBackPressed()
    }


}
