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


class ActivityHome : AppCompactBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_home)


    }
}