package com.ids.mercury.controller.Activities

import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_success.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.toolbar.*


class ActivitySuccess : AppCompactBase() {
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        init()
    }


    private fun init(){
       btBack.setOnClickListener{
           super.onBackPressed()
       }

        btHome.setOnClickListener{
            val mIntent = Intent(this, ActivityHome::class.java)
            finishAffinity()
            startActivity(mIntent)
        }

    }



}
