package com.ids.mercury.controller.Activities


import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import com.ids.mercury.BuildConfig
import com.ids.mercury.R
import com.ids.mercury.controller.Base.ActivityBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.controller.MyApplication.Companion.BASE_URLS
import com.ids.mercury.model.FirebaseBaseUrlsArray
import com.ids.mercury.model.response.ResponseGymClasses
import com.ids.mercury.model.response.ResponseMenus
import com.ids.mercury.model.response.ResponseMessage
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.loading_trans.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ActivitySplash : ActivityBase() ,MenusDataListener ,ApiListener{
    var mFirebaseRemoteConfig: FirebaseRemoteConfig? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //  MyApplication.isLoggedIn = true
        getFirebasePrefs()

}



    fun addDevice () {
        var generalNot=1
        if(!MyApplication.notificationGeneral)
            generalNot=0
        val token = ""
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.wtf("token1", "token is " + token)
            }


            RetrofitClient.client?.create(RetrofitInterface::class.java)
                ?.addDevice(
                    AppHelper.getDeviceName(),
                    AppHelper.getAndroidVersion(),
                    token,
                    AppConstants.DEVICE_TYPE_ANDROID,
                    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID),
                    MyApplication.dateFormat!!.format(Calendar.getInstance().time),
                    generalNot,
                    BuildConfig.VERSION_CODE.toString(),
                    ""
                )?.enqueue(object :
                    Callback<ResponseMessage> {
                    override fun onResponse(
                        call: Call<ResponseMessage>,
                        response: Response<ResponseMessage>
                    ) {
                        if(response.body()!!.success=="1"){
                            MyApplication.memberId=response.body()!!.memberId!!
                        }
                    }
                    override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    }
                })

        })

    }



    private fun getFirebasePrefs() {
        mFirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        mFirebaseRemoteConfig!!.setConfigSettingsAsync(configSettings)
        mFirebaseRemoteConfig!!.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    setFirebaseData()
                    checkUpdate()
                }
                else
                    next()

               // nextStep()
            }
    }


    private fun setFirebaseData(){

        if(mFirebaseRemoteConfig!=null){
        try{
            BASE_URLS = Gson().fromJson(mFirebaseRemoteConfig!!.getString(AppConstants.FIREBASE_URL_LIST), FirebaseBaseUrlsArray::class.java)
           if(BASE_URLS!=null && BASE_URLS!!.android!!.size>0){
                var myUrl=BASE_URLS!!.android!!.find { it.version == BuildConfig.VERSION_NAME.toDouble() }
                if(myUrl!=null){
                    MyApplication.BASE_URL=myUrl.url!!
                }else
                    MyApplication.BASE_URL=BASE_URLS!!.android!!.maxByOrNull { it.version!! }!!.url!!
            }}catch (e:Exception){}
        MyApplication.URL_PAYMENT_JS=mFirebaseRemoteConfig!!.getString(AppConstants.FIREBASE_PAYMENT_JS_URL)
        MyApplication.URL_CREATE_CHECKOUT_SESSION=mFirebaseRemoteConfig!!.getString(AppConstants.FIREBASE_CHECKOUT_SESSION_URL)
        MyApplication.force_update=mFirebaseRemoteConfig!!.getBoolean(AppConstants.FIREBASE_FORCE_UPDATE)
        MyApplication.android_version=mFirebaseRemoteConfig!!.getString(AppConstants.FIREBASE_ANDROID_VERSION)
        logw("firebase_array_size","..."+BASE_URLS!!.android!!.size)

        }

    }

    private fun checkUpdate(){
        if(MyApplication.android_version.isNotEmpty()){
        if (MyApplication.android_version.toDouble() > BuildConfig.VERSION_NAME.toDouble()) {
            if(MyApplication.force_update)
                showDialogForceUpdate(this)
            else
                showDialogUpdate(this)
        } else {
            next()
        } }
    }

    private fun goLogin(){
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,ActivityLogin::class.java))
            finish()
        }, 500)
    }
    private fun goToHome(){
        CallApi.getSuggestedClasses(this,this)
        startActivity(Intent(this,ActivityHome::class.java))
        finish()
    }

    private fun showDialogForceUpdate(activity: Activity) {

        val builder = AlertDialog.Builder(activity)
        val textView: TextView
        val inflater = activity.layoutInflater
        val textEntryView = inflater.inflate(R.layout.item_dialog, null)
        textView = textEntryView.findViewById(R.id.dialogMsg)
        textView.gravity = Gravity.START

        textView.text = getString(R.string.update_message)
        builder.setTitle(getString(R.string.update_title))
        builder.setCancelable(false)

        builder.setView(textEntryView)
            .setNegativeButton(getString(R.string.update)) { dialog, _ ->
                dialog.dismiss()
                val appPackageName = activity.packageName
                try {

                    activity.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)
                        )
                    )
                    activity.finish()

                } catch (anfe: ActivityNotFoundException) {

                    activity.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)
                        )
                    )
                    activity.finish()

                }
            }
        val d = builder.create()
        d.setOnShowListener {
            d.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(this@ActivitySplash, R.color.colorPrimary))
            d.getButton(AlertDialog.BUTTON_NEGATIVE).transformationMethod = null
            d.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false
        }

        d.setCancelable(false)

        d.show()
    }


    private fun showDialogUpdate(activity: Activity) {

        val builder = AlertDialog.Builder(activity)
        val textView: TextView
        val inflater = activity.layoutInflater
        val textEntryView = inflater.inflate(R.layout.item_dialog, null)
        textView = textEntryView.findViewById(R.id.dialogMsg)
        textView.gravity = Gravity.CENTER
        textView.text = activity.resources.getString(R.string.update_message)
        builder.setTitle(activity.resources.getString(R.string.update_title))

        builder.setView(textEntryView)
            .setPositiveButton(activity.resources.getString(R.string.update)) { dialog, _ ->
                dialog.dismiss()
                val appPackageName = activity.packageName
                try {
                    activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))

                    activity.finish()

                } catch (anfe: android.content.ActivityNotFoundException) {
                    activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                    activity.finish()

                }
            }
            .setNegativeButton(activity.resources.getString(R.string.update_cancel)) { dialog, _ ->
                dialog.dismiss()

                next()
            }

        val d = builder.create()
        d.setOnShowListener {

            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this@ActivitySplash, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this@ActivitySplash, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).transformationMethod = null
            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).isAllCaps = false

            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this@ActivitySplash, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this@ActivitySplash, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).transformationMethod = null
            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).isAllCaps = false
        }
        d.setCancelable(false)

        d.show()

    }

    private fun next(){
        getMenus()
        addDevice()
        if(MyApplication.isLoggedIn)
            checkLogin()
        else
            goLogin()
    }

    private fun getMenus(){
        GetMenusAPI.getMenus(this,"",-1,0,this)
    }

    override fun onDataMenuRetrieved(success: Boolean, responseMenus: ResponseMenus) {

    }




    fun checkLogin () {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.login(MyApplication.cnm, MyApplication.cps)?.enqueue(object : Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    if(response.body()!!.success=="1"){
                        MyApplication.memberId=response.body()!!.memberId!!
                        AppHelper.getMemberDetails()
                        goToHome()
                    }else{
                        resetLoginParams()
                        goLogin()
                    }

                }
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                   resetLoginParams()
                   goLogin()
                }
            })
    }


    private fun resetLoginParams(){
        MyApplication.isLoggedIn=false
        MyApplication.cnm=""
        MyApplication.cps=""
    }

    override fun onDataRetrieved(success: Boolean, response: Any) {

    }


}
