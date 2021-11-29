@file:Suppress("NAME_SHADOWING")
package com.ids.mercury.controller

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.ids.mercury.model.FirebaseBaseUrlsArray
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.AppConstants
import java.util.*

class MyApplication : Application() {
    companion object {
        internal lateinit var instance: MyApplication
        var isDebug: Boolean = true
        var showLogs: Boolean = true
        var BASE_URL = "http://149.3.154.210:5083/generalservices.asmx/"
        var dateFormat:java.text.DateFormat? = java.text.SimpleDateFormat("yyyy/MM/dd", java.util.Locale.ENGLISH)
        lateinit var sharedPreferences : SharedPreferences
        lateinit var sharedPreferencesEditor : SharedPreferences.Editor
        var selectedFragmentTag : String ?=""
        var languageCode : String
            get() = sharedPreferences.getString(AppConstants.SELECTED_LANGUAGE, AppConstants.LANG_ENGLISH)!!
            set(value) { sharedPreferencesEditor.putString(AppConstants.SELECTED_LANGUAGE, value).apply() }
        var UNIQUE_REQUEST_CODE = 0
        var selectedFragment  : Fragment?=null
        var memberId : Int
            get() = sharedPreferences.getInt(AppConstants.MEMBER_ID, 0)!!
            set(value) { sharedPreferencesEditor.putInt(AppConstants.MEMBER_ID, value).apply() }

        var notificationGeneral: Boolean
            get() = sharedPreferences.getBoolean(AppConstants.GENERAL_NOTIFICATION, true)
            set(value) {
                sharedPreferencesEditor.putBoolean(AppConstants.GENERAL_NOTIFICATION, value).apply()
            }

        var isLoggedIn: Boolean
            get() = sharedPreferences.getBoolean(AppConstants.IS_LOGGED_IN, true)
            set(value) {
                sharedPreferencesEditor.putBoolean(AppConstants.IS_LOGGED_IN, value).apply()
            }

        var cnm : String
            get() = sharedPreferences.getString(AppConstants.CNM, "")!!
            set(value) { sharedPreferencesEditor.putString(AppConstants.CNM, value).apply() }

        var cps : String
            get() = sharedPreferences.getString(AppConstants.CPS, "")!!
            set(value) { sharedPreferencesEditor.putString(AppConstants.CPS, value).apply() }

        var BASE_URLS: FirebaseBaseUrlsArray ?= null
        var URL_PAYMENT_JS=""
        var URL_CREATE_CHECKOUT_SESSION=""
        var force_update=false
        var android_version=""
        var verificationTitle=""
        var lastUsedOrderId : Long ?=0
        var tempMemberId=""

         var pageItemCount=4
         var gridRecyclerCount=2
          var memberDetails : Member?=null
        var selectedImage=""
        var selectedItemDialog="+961"
        var selectedGymPackageId=0
        var selectedPtPackageId=0
        var selectedActivityId=0
        var arraySuggestedClasses=java.util.ArrayList<GymClass>()
        var selectedAcademy : Activity?=null
        var selectedCsr : Child?=null

        var sEmail=""
        var rEmail=""
        var sPassword=""
        var emailSubject=""
    }


    override fun onCreate() {
        super.onCreate()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        sharedPreferencesEditor = sharedPreferences.edit()
        instance=this
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

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
