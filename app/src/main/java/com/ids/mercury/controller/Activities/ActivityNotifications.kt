package com.ids.mercury.controller.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterCountryCodes
import com.ids.mercury.controller.Adapters.AdapterNotifcations
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.CountryCodes
import com.ids.mercury.model.Notifications
import com.ids.mercury.utils.hide
import com.ids.mercury.utils.setBackgroundTint
import com.ids.mercury.utils.setTint
import com.ids.mercury.utils.show
import kotlinx.android.synthetic.main.activity_notifications.*
import kotlinx.android.synthetic.main.toolbar.*


class ActivityNotifications : AppCompactBase(),RVOnItemClickListener {
    private var arrayNotifications=java.util.ArrayList<Notifications>()
    lateinit var adapter : AdapterNotifcations

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        init()
        listeners()
    }

    private fun init(){
        setData()
        btProfile.setBackgroundResource(R.drawable.notification)
        btProfile.show()

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
        }
    }

    private fun setData(){
        arrayNotifications.clear()
        arrayNotifications.add(Notifications(1,"Notification 1","","",false))
        arrayNotifications.add(Notifications(2,"Notification 2","","",false))
        arrayNotifications.add(Notifications(3,"Notification 3","","",false))
        arrayNotifications.add(Notifications(4,"Notification 4","","",false))
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvNotification.layoutManager = layoutManager
        adapter = AdapterNotifcations(arrayNotifications,this)
        rvNotification.adapter = adapter
    }

    override fun onItemClicked(view: View, position: Int) {
        arrayNotifications[position].expanded = !arrayNotifications[position].expanded!!
        adapter.notifyItemChanged(position)
    }


}