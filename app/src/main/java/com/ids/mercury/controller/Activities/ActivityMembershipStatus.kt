package com.ids.mercury.controller.Activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterMembership
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.MemberShip
import com.ids.mercury.model.response.ResponseMembership
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_loyality_points.tvNodata
import kotlinx.android.synthetic.main.activity_membership_status.*

import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class ActivityMembershipStatus : AppCompactBase(),RVOnItemClickListener {
    private var arrayData=java.util.ArrayList<MemberShip>()
    lateinit var adapter : AdapterMembership
    var selectedTab=AppConstants.TYPE_GYM



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membership_status)
        init()
        listeners()
        getGymData()
    }

    private fun setTabs(){
        btGym.setOnClickListener{
            btGym.setBackgroundColor(ContextCompat.getColor(this,R.color.gray_button))
            btPersonalTrainer.setBackgroundResource(0)
            getGymData()
            btRenew.text = getString(R.string.renew_membership)
            selectedTab=AppConstants.TYPE_GYM
        }

        btPersonalTrainer.setOnClickListener{
            btPersonalTrainer.setBackgroundColor(ContextCompat.getColor(this,R.color.gray_button))
            btGym.setBackgroundResource(0)
            getPersonalTrainerData()
            btRenew.text = getString(R.string.renew_pt)
            selectedTab=AppConstants.TYPE_PT
        }
    }


    private fun init(){
        tvToolbarTitle.text=""
        tvToolbarTitle.show()
        btProfile.show()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{startActivity(Intent(this,ActivityProfile::class.java))}
        setTabs()
        btRenew.setOnClickListener{
            startActivity(Intent(this,ActivityRenewMembership::class.java).putExtra("type",selectedTab))
        }
    }


    override fun onItemClicked(view: View, position: Int) {

    }

    fun getGymData () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getGymMembers(MyApplication.memberId.toString())?.enqueue(object :
                Callback<ResponseMembership> {
                override fun onResponse(
                    call: Call<ResponseMembership>,
                    response: Response<ResponseMembership>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1" && response.body()!!.gymMembers!!.size>0){
                        tvNodata.hide()
                        setData(response.body()!!.gymMembers)
                    }else
                        tvNodata.show()

                }
                override fun onFailure(call: Call<ResponseMembership>, t: Throwable) {
                    loading.hide()
                    tvNodata.show()
                }
            })
    }


    fun getPersonalTrainerData () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getGymPts(MyApplication.memberId.toString())?.enqueue(object :
                Callback<ResponseMembership> {
                override fun onResponse(
                    call: Call<ResponseMembership>,
                    response: Response<ResponseMembership>
                ) {
                    loading.hide()
                   if(response.body()!!.success=="1" && response.body()!!.gymMembers!!.size>0){
                        tvNodata.hide()
                        setData(response.body()!!.gymMembers)
                   }else
                        tvNodata.show()

                }
                override fun onFailure(call: Call<ResponseMembership>, t: Throwable) {
                    loading.hide()
                    tvNodata.show()
                }
            })
    }

    private fun setData(members: ArrayList<MemberShip>?) {
        arrayData.clear()
        arrayData.addAll(members!!)
       /* arrayData.add(MemberShip(1,1,1,1,"01/12/2021","01/12/2022","1 Month","Active","1"))
        arrayData.add(MemberShip(2,2,2,2,"01/12/2021","01/12/2022","2 Month","Expired","2"))
      */
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvMemberShip.layoutManager = layoutManager
        adapter = AdapterMembership(arrayData,this,selectedTab)
        rvMemberShip.adapter = adapter

    }

}