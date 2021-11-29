package com.ids.mercury.controller.Activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterGiftCards
import com.ids.mercury.controller.Adapters.AdapterHistory
import com.ids.mercury.controller.Adapters.AdapterPaymentHistory
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.GiftCards
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_gift_card.*
import kotlinx.android.synthetic.main.activity_gift_card.etEmail
import kotlinx.android.synthetic.main.activity_gift_card.etPhoneNumber
import kotlinx.android.synthetic.main.activity_guest_passes.*
import kotlinx.android.synthetic.main.activity_loyality_points.tvNodata
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_signup.linearPhone
import kotlinx.android.synthetic.main.fragment_signup.tvCountryCode

import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ActivityGiftCard : AppCompactBase(),RVOnItemClickListener {
    private var arrayGiftCards=java.util.ArrayList<GiftCards>()
    lateinit var adapter : AdapterGiftCards
    lateinit var  shake: Animation
    private var selectedCardName=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift_card)
        init()
        listeners()
    }

    private fun init(){
        shake =  AnimationUtils.loadAnimation(this, R.anim.shake)
        btProfile.show()
        tvToolbarTitle.text=getString(R.string.purchase_gift)
        getGiftCards()


    }

    private fun listeners(){
        btBack.setOnClickListener{backClick()}
        btProfile.setOnClickListener{
            startActivity(Intent(this,ActivityProfile::class.java))
        }
        btSubmit.setOnClickListener{
            checkInputs()
        }

        setDatesPicker()
    }

    private fun checkInputs(){
        when {
            //etFullName.text.isNullOrEmpty() -> etFullName.startAnimation(shake)
            etEmail.text.isNullOrEmpty() -> etEmail.startAnimation(shake)
            !AppHelper.isValidEmail(etEmail.text.toString()) -> etEmail.startAnimation(shake)
            etPhoneNumber.text.isNullOrEmpty() -> linearPhone.startAnimation(shake)
           // tvDate.text.isNullOrEmpty() -> tvDate.startAnimation(shake)
            else ->sendGiftCard() //sendRequestEmail()
        }
    }

    private fun submit(){
        startActivity(Intent(this,ActivitySuccess::class.java))
    }


    override fun onItemClicked(view: View, position: Int) {
       resetSelection()
       arrayGiftCards[position].selected=true
       adapter.notifyDataSetChanged()
        Handler(Looper.getMainLooper()).postDelayed({
            linearStep2.show()
            linearStep1.hide()
            selectedCardName=arrayGiftCards[position].name!!
        }, 200)
    }

    private fun resetSelection(){
        for (i in arrayGiftCards.indices)
            arrayGiftCards[i].selected=false
    }

    fun backClick(){
        if(linearStep2.isVisible){
            linearStep2.hide()
            linearStep1.show()
            resetSelection()
            try{adapter.notifyDataSetChanged()}catch (e:Exception){}
        }else
            super.onBackPressed()
    }

    fun getGiftCards () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getGiftCards()?.enqueue(object :
                Callback<ResponseGiftCard> {
                override fun onResponse(
                    call: Call<ResponseGiftCard>,
                    response: Response<ResponseGiftCard>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1" && response.body()!!.referrals!!.size>0){
                        tvNodata.hide()
                        setData(response.body()!!.referrals)
                    }else
                        tvNodata.show()

                }
                override fun onFailure(call: Call<ResponseGiftCard>, t: Throwable) {
                    loading.hide()
                    tvNodata.show()
                }
            })
    }

    private fun setData(history: ArrayList<GiftCards>?) {
        arrayGiftCards.clear()
        arrayGiftCards.addAll(history!!)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvCards.layoutManager = layoutManager
        adapter = AdapterGiftCards(arrayGiftCards,this)
        rvCards.adapter = adapter

    }

    private fun setDatesPicker(){

        var textDateListener: DatePickerDialog.OnDateSetListener
        var textDateCalendar: Calendar = Calendar.getInstance()
        textDateListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // TODO Auto-generated method stub
                textDateCalendar.set(Calendar.YEAR, year)
                textDateCalendar.set(Calendar.MONTH, monthOfYear)
                textDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                tvDate.text = AppHelper.dateFormat1.format(textDateCalendar.time)

            }
        llDate.setOnClickListener {
            DatePickerDialog(
                this,
                textDateListener,
                textDateCalendar.get(Calendar.YEAR),
                textDateCalendar.get(Calendar.MONTH),
                textDateCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


    fun sendRequestEmail() {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.sendrequestEmail(MyApplication.memberId.toString(),"2")?.enqueue(object :
                Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1"){
                       submit()
                    }else
                       toastt(response.body()!!.message!!)

                }
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    loading.hide()
                    toastt(getString(R.string.try_again))
                }
            })
    }

    override fun onBackPressed() {
        backClick()
    }


    fun sendGiftCard () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.sendGiftCard(MyApplication.memberId.toString(),etEmail.text.toString(),etPhoneNumber.text.toString(),
                selectedCardName
            )?.enqueue(object :
                Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1"){
                        submit()
                    }else
                        toastt(response.body()!!.message!!)

                }
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    loading.hide()
                    toastt(getString(R.string.try_again))
                }
            })
    }
}