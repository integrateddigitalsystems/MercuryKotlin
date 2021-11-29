package com.ids.mercury.controller.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterNotifcations
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.ItemNotification
import com.ids.mercury.model.response.ResponseNotifications
import com.ids.mercury.utils.*

import kotlinx.android.synthetic.main.activity_notifications.*
import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActivityNotifications : AppCompactBase(),RVOnItemClickListener ,ApiListener{
    private var arrayNotifications=java.util.ArrayList<ItemNotification>()
    lateinit var adapter : AdapterNotifcations

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        init()
        listeners()
    }

    private fun init(){
        getNotifications()
        btProfile.setBackgroundResource(R.drawable.notification)
        btProfile.show()
        setNotificationIcon()

    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{
            if(!MyApplication.notificationGeneral){
                btProfile.setBackgroundTint(R.color.button_blue)
            }else{
                btProfile.setBackgroundTint(R.color.white)
            }
            MyApplication.notificationGeneral=!MyApplication.notificationGeneral
            CallApi.addDevice(this,this)
        }
    }

    private fun setNotificationIcon(){
        if(MyApplication.notificationGeneral){
            btProfile.setBackgroundTint(R.color.button_blue)
        }else{
            btProfile.setBackgroundTint(R.color.white)
        }
    }

    private fun setData(items: ArrayList<ItemNotification>?) {
        arrayNotifications.clear()
        arrayNotifications.addAll(items!!)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvNotification.layoutManager = layoutManager
        adapter = AdapterNotifcations(arrayNotifications,this)
        rvNotification.adapter = adapter
    }

    override fun onItemClicked(view: View, position: Int) {
        arrayNotifications[position].expanded = !arrayNotifications[position].expanded!!
        adapter.notifyItemChanged(position)
    }


    fun getNotifications () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getMemberNotifications(MyApplication.memberId.toString())?.enqueue(object :
                Callback<ResponseNotifications> {
                override fun onResponse(
                    call: Call<ResponseNotifications>,
                    response: Response<ResponseNotifications>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1" && response.body()!!.items!!.size>0){
                        tvNodata.hide()
                        setData(response.body()!!.items)
                    }else
                        tvNodata.show()

                }
                override fun onFailure(call: Call<ResponseNotifications>, t: Throwable) {
                    loading.hide()
                    tvNodata.show()
                }
            })
    }

    override fun onDataRetrieved(success: Boolean, response: Any) {

    }

}