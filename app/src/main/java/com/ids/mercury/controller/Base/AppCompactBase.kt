package com.ids.mercury.controller.Base

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ids.mercury.controller.Activities.*
import com.ids.mercury.utils.AppHelper
import com.ids.mercury.utils.LocaleUtils
import com.ids.mercury.utils.show
import kotlinx.android.synthetic.main.toolbar.*

import java.util.*


open class AppCompactBase : AppCompatActivity() {
    private var decorView: View? = null

    init {
        LocaleUtils.updateConfig(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppHelper.setLocal(this)
        AppHelper.handleCrashes(this)


    }

    override fun onResume() {
        super.onResume()

    }


    override fun attachBaseContext(newBase: Context) {
        var newBase = newBase
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val config = newBase.resources.configuration
            config.setLocale(Locale.getDefault())
            newBase = newBase.createConfigurationContext(config)
        }
        super.attachBaseContext(newBase)
    }
}
