package com.ids.mercury.utils


import android.content.Context
import android.provider.Settings
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ids.mercury.BuildConfig
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.ResponseGymClasses
import com.ids.mercury.model.response.ResponseMemberDetails
import com.ids.mercury.model.response.ResponseMenus
import com.ids.mercury.model.response.ResponseMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CallApi {
    companion object{
        lateinit var apiListener :ApiListener

        fun getSuggestedClasses(context: Context,listener: ApiListener){
            apiListener=listener
            RetrofitClient.client?.create(RetrofitInterface::class.java)
                ?.getSuggestedGymClasses()?.enqueue(object :
                    Callback<ResponseGymClasses> {
                    override fun onResponse(
                        call: Call<ResponseGymClasses>,
                        response: Response<ResponseGymClasses>
                    ) {
                        if(response.body()!!.success=="1"){
                            MyApplication.arraySuggestedClasses.clear()
                            MyApplication.arraySuggestedClasses.addAll(response.body()!!.gymClasses!!)
                            listener.onDataRetrieved(true,response.body()!!)
                        }else{
                            listener.onDataRetrieved(false, ResponseGymClasses())
                        }

                    }
                    override fun onFailure(call: Call<ResponseGymClasses>, t: Throwable) {
                        listener.onDataRetrieved(false,ResponseGymClasses())
                    }
                })
        }


        fun addDevice (context: Context,listener: ApiListener) {
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
                        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID),
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
                                // MyApplication.memberId=response.body()!!.memberId!!
                                MyApplication.addDeviceId=response.body()!!.id!!
                            }
                        }
                        override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                        }
                    })

            })

        }




        fun updateUserDevice () {
            RetrofitClient.client?.create(RetrofitInterface::class.java)
                ?.updateUserDevice(MyApplication.memberId.toString(),MyApplication.addDeviceId.toString())?.enqueue(object :
                    Callback<ResponseMessage> {
                    override fun onResponse(
                        call: Call<ResponseMessage>,
                        response: Response<ResponseMessage>
                    ) {
                        wtf("update_user_device "+response.body()!!.memberId!!.toString()+"  ")
                    }
                    override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    }
                })
        }





    }


}