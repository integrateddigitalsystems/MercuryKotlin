package com.ids.mercury.controller.Activities

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterHistory
import com.ids.mercury.controller.Adapters.AdapterLoyalityPoints
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.History
import com.ids.mercury.model.response.ResponseHistory
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_loyality_points.*

import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class ActivityHistory : AppCompactBase(),RVOnItemClickListener {
    private var arrayHistory=java.util.ArrayList<History>()
    lateinit var adapter : AdapterHistory



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        init()
        listeners()
    }

    private fun init(){
        btProfile.show()
        tvToolbarTitle.text=getString(R.string.history)
        getLoyalityPoints()


    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{super.onBackPressed()}
    }


    override fun onItemClicked(view: View, position: Int) {

    }

    fun getLoyalityPoints () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getLoyalityHistory(MyApplication.memberId.toString())?.enqueue(object :
                Callback<ResponseHistory> {
                override fun onResponse(
                    call: Call<ResponseHistory>,
                    response: Response<ResponseHistory>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1" && response.body()!!.memberLoyaltyPoints!!.size>0){
                        tvNodata.hide()
                        setData(response.body()!!.memberLoyaltyPoints)
                    }else
                        tvNodata.show()

                }
                override fun onFailure(call: Call<ResponseHistory>, t: Throwable) {
                    loading.hide()
                    tvNodata.show()
                }
            })
    }

    private fun setData(memberLoyaltyPoints: ArrayList<History>?) {
        arrayHistory.clear()
        arrayHistory.addAll(memberLoyaltyPoints!!)
/*        arrayHistory.add(History(1,1000,"description","10/10/2020"))
        arrayHistory.add(History(1,2000,"description","10/10/2020"))*/
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvLoyality.layoutManager = layoutManager
        adapter = AdapterHistory(arrayHistory,this)
        rvLoyality.adapter = adapter

    }

}