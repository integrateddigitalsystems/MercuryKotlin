package com.ids.mercury.utils


import android.content.Context
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.ResponseGymClasses
import com.ids.mercury.model.response.ResponseMenus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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


    }
}