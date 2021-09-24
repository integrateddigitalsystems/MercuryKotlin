package com.ids.mercury.controller.Activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterGiftCards
import com.ids.mercury.controller.Adapters.AdapterGymPackages
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.GiftCards
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_buy_extra.*
import kotlinx.android.synthetic.main.activity_renew_membership.*
import kotlinx.android.synthetic.main.activity_renew_membership.btProceed
import kotlinx.android.synthetic.main.activity_renew_membership.linearPackages
import kotlinx.android.synthetic.main.activity_renew_membership.tvSelectedPackage

import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ActivityBuyExtraGuest : AppCompactBase(),RVOnItemClickListener ,PaymentListener{
    lateinit var adapter : AdapterGiftCards
    lateinit var  shake: Animation
    lateinit var dialog :Dialog
    private var arrayGymPackages=java.util.ArrayList<GymPackage>()
    lateinit var adapterGymPackages : AdapterGymPackages


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_extra)
        init()
        listeners()
    }

    private fun init(){
        MyApplication.selectedGymPackageId=0
        shake =  AnimationUtils.loadAnimation(this, R.anim.shake)
        btProfile.show()
        tvToolbarTitle.text=getString(R.string.buy_guest_pass)
        getGymPackages()
    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{
            startActivity(Intent(this,ActivityProfile::class.java))
        }
        btProceed.setOnClickListener{
            if(MyApplication.selectedGymPackageId!=0){
                var price=arrayGymPackages.find { it.id==MyApplication.selectedGymPackageId }!!.amount!!.toInt()
                PaymentApiDialog.showPaymentDialog(this, this,price.toString())
            }
            else
                createDialog(getString(R.string.must_choose_package))
        }

    }

    override fun onItemClicked(view: View, position: Int) {
        MyApplication.selectedGymPackageId = adapterGymPackages.items[position].id!!
        tvSelectedPackage.text=adapterGymPackages.items[position].name!!
        dialog.dismiss()
    }

    fun getGymPackages () {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getGuestPassPackages()?.enqueue(object :
                Callback<ResponseGymPackages> {
                override fun onResponse(
                    call: Call<ResponseGymPackages>,
                    response: Response<ResponseGymPackages>
                ) {

                    if(response.body()!!.success=="1" && response.body()!!.gymPackages!!.size>0){
                        arrayGymPackages.clear()
                        arrayGymPackages.addAll(response.body()!!.gymPackages!!)
                    }
                    setPackageListener()

                }
                override fun onFailure(call: Call<ResponseGymPackages>, t: Throwable) {
                    setPackageListener()
                }
            })
    }

    private fun setPackageListener(){
        linearPackages.setOnClickListener{
            if(arrayGymPackages.size > 0)
               showGymPackages()
            else
                createDialog(getString(R.string.no_data_to_display))
        }
    }


    private fun showGymPackages(){
        dialog = Dialog(this, R.style.dialogWithoutTitle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.popup_recycler)
        dialog.setCancelable(true)
        val rv: RecyclerView = dialog.findViewById(R.id.rvData)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.layoutManager = layoutManager
        adapterGymPackages = AdapterGymPackages(arrayGymPackages,this)
        rv.adapter = adapterGymPackages
        try{
            var item=arrayGymPackages.find { it.id!! == MyApplication.selectedGymPackageId }
            var position=arrayGymPackages.indexOf(item!!)
            rv.scrollToPosition(position)}catch (e:Exception){}
        dialog.show()
    }

    override fun onFinishPayment(success: Boolean) {
        if(success)
            startActivity(Intent(this,ActivitySuccess::class.java))
    }


}