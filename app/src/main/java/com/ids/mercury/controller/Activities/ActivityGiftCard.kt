package com.ids.mercury.controller.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterHistory
import com.ids.mercury.controller.Adapters.AdapterPaymentHistory
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.History
import com.ids.mercury.model.response.InvoicePayments
import com.ids.mercury.model.response.ResponsePaymentHistory
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_loyality_points.*

import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class ActivityGiftCard : AppCompactBase(),RVOnItemClickListener {
    private var arrayHistory=java.util.ArrayList<InvoicePayments>()
    lateinit var adapter : AdapterPaymentHistory



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        init()
        listeners()
    }

    private fun init(){
        btProfile.show()
        tvToolbarTitle.text=getString(R.string.purchase_gift)
        getLoyalityPoints()


    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{
            startActivity(Intent(this,ActivityProfile::class.java))
        }
    }


    override fun onItemClicked(view: View, position: Int) {

    }

    fun getLoyalityPoints () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getMemberPayments(MyApplication.memberId.toString())?.enqueue(object :
                Callback<ResponsePaymentHistory> {
                override fun onResponse(
                    call: Call<ResponsePaymentHistory>,
                    response: Response<ResponsePaymentHistory>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1" && response.body()!!.invoicePayments!!.size>0){
                        tvNodata.hide()
                        setData(response.body()!!.invoicePayments)
                    }else
                        tvNodata.show()

                }
                override fun onFailure(call: Call<ResponsePaymentHistory>, t: Throwable) {
                    loading.hide()
                    tvNodata.show()
                }
            })
    }

    private fun setData(history: ArrayList<InvoicePayments>?) {
        arrayHistory.clear()
        arrayHistory.addAll(history!!)
        /*  arrayHistory.add(InvoicePayments(1,1,"1000","12/10/2020","$","aa"))
          arrayHistory.add(InvoicePayments(2,2,"2000","10/10/2020","$","bbb"))
        */  val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHistory.layoutManager = layoutManager
        adapter = AdapterPaymentHistory(arrayHistory,this)
        rvHistory.adapter = adapter

    }

}