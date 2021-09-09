package com.ids.mercury.utils

import android.content.Context
import com.ids.mercury.model.response.ResponseMenus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetMenusAPI {
    companion object{
        lateinit var menusDataListener :MenusDataListener

        fun getMenus(context: Context,key:String,showInDrawer:Int,parentId:Int,listener: MenusDataListener){
            menusDataListener=listener
            RetrofitClient.client?.create(RetrofitInterface::class.java)
                ?.getMenus(key,showInDrawer,parentId)?.enqueue(object :
                    Callback<ResponseMenus> {
                    override fun onResponse(
                        call: Call<ResponseMenus>,
                        response: Response<ResponseMenus>
                    ) {
                      if(response.body()!!.success=="1"){
                         listener.onDataMenuRetrieved(true,response.body()!!)
                      }else{
                          listener.onDataMenuRetrieved(false, ResponseMenus())
                      }

                    }
                    override fun onFailure(call: Call<ResponseMenus>, t: Throwable) {
                        listener.onDataMenuRetrieved(false,ResponseMenus())
                    }
                })
        }


    }
}