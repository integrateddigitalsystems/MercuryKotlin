package com.ids.mercury.controller.Activities


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.Animation
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterCountryCodes
import com.ids.mercury.controller.Adapters.AdapterHistory
import com.ids.mercury.controller.Adapters.AdapterMembership
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.CountryArray
import com.ids.mercury.model.CountryCodes
import com.ids.mercury.model.response.MemberShip
import com.ids.mercury.model.response.ResponseMembership
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_loyality_points.*
import kotlinx.android.synthetic.main.activity_loyality_points.rvLoyality
import kotlinx.android.synthetic.main.activity_loyality_points.tvNodata
import kotlinx.android.synthetic.main.activity_membership_status.*

import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class ActivityRenewMembership : AppCompactBase(),RVOnItemClickListener {
    private var arrayData=java.util.ArrayList<MemberShip>()
    lateinit var adapter : AdapterCountryCodes
    var type=0
    private var arrayCountries=java.util.ArrayList<CountryCodes>()
    lateinit var adapterPackages : AdapterCountryCodes
    lateinit var dialog :Dialog
    lateinit var  shake: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renew_membership)
        init()
        listeners()

    }




    private fun init(){

        type=intent.getIntExtra("type",0)
        if(type==AppConstants.TYPE_GYM)
            tvToolbarTitle.text=getString(R.string.renew_membership)
        else
            tvToolbarTitle.text=getString(R.string.renew_pt)

    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{startActivity(Intent(this,ActivityProfile::class.java))}
    }


    override fun onItemClicked(view: View, position: Int) {

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
                    //  if(response.body()!!.success=="1" && response.body()!!.gymMembers!!.size>0){
                    tvNodata.hide()
                   // setData(response.body()!!.gymMembers)
                    /*      }else
                              tvNodata.show()*/

                }
                override fun onFailure(call: Call<ResponseMembership>, t: Throwable) {
                    loading.hide()
                    tvNodata.show()
                }
            })
    }

/*    private fun setData(members: ArrayList<MemberShip>?) {
        arrayData.clear()
        arrayData.addAll(members!!)
        arrayData.add(MemberShip(1,1,1,1,"01/12/2021","01/12/2022","1 Month","Active","1"))
        arrayData.add(MemberShip(2,2,2,2,"01/12/2021","01/12/2022","2 Month","Expired","2"))
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvMemberShip.layoutManager = layoutManager
        adapter = AdapterMembership(arrayData,this)
        rvMemberShip.adapter = adapter

    }*/


    private fun showPackages(){
        arrayCountries.clear()
        arrayCountries.addAll(Gson().fromJson(loadJSONFromAssets("countries.json"), CountryArray::class.java).countries!!)
        dialog = Dialog(this, R.style.dialogWithoutTitle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.popup_country_codes)
        dialog.setCancelable(true)
        val rv: RecyclerView = dialog.findViewById(R.id.rvCountryCodes)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.layoutManager = layoutManager
        adapter = AdapterCountryCodes(arrayCountries,this)
        rv.adapter = adapter
        try{
            var item=arrayCountries.find { it.code!!.replace("+","").trim()==MyApplication.selectedItemDialog.replace("+","").trim() }
            var position=arrayCountries.indexOf(item!!)
            rv.scrollToPosition(position)}catch (e:Exception){}
        dialog.show()
    }

}