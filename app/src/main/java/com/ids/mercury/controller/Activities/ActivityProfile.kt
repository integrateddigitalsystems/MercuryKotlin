package com.ids.mercury.controller.Activities

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterCountryCodes
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.custom.InputFilterMinMax
import com.ids.mercury.model.CountryArray
import com.ids.mercury.model.CountryCodes
import com.ids.mercury.model.response.ResponseMemberDetails
import com.ids.mercury.model.response.ResponseMessage
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.etEmail
import kotlinx.android.synthetic.main.activity_profile.etPhoneNumber
import kotlinx.android.synthetic.main.activity_profile.linearPhone
import kotlinx.android.synthetic.main.activity_profile.tvCountryCode

import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ActivityProfile : AppCompactBase(),RVOnItemClickListener {
    private var arrayCountries=java.util.ArrayList<CountryCodes>()
    lateinit var adapter : AdapterCountryCodes
    lateinit var dialog :Dialog
    lateinit var  shake: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
        listeners()
    }

    private fun init(){
        shake =  AnimationUtils.loadAnimation(this, R.anim.shake)
        etPhoneNumber.filters=arrayOf(InputFilterMinMax(1, 99999999))
        setData()
        btLogout.show()
        tvToolbarTitle.text=getString(R.string.profile)
/*        if(MyApplication.memberDetails!=null)
            setData()
        else*/
            getMemberDetails()

    }

    private fun listeners(){
        btBack.setOnClickListener{
            hideKeyboard()
            super.onBackPressed()}
        btEditUpdate.setOnClickListener{
            if(linearDisplayedData.isVisible){
                linearDisplayedData.hide()
                linearDataEdit.show()
                btEditUpdate.text=getString(R.string.update)
            }else{
                checkAndSubmit()
            }
            setDatesPicker()
        }

        tvCountryCode.setOnClickListener{
            showCountries()
        }

        btLogout.setOnClickListener{
            logout(this,getString(R.string.logout_message))
        }
        
        btLoyalityPoints.setOnClickListener{
            startActivity(Intent(this,ActivityLoyalityPoints::class.java))
        }

        btHistory.setOnClickListener{
            startActivity(Intent(this,ActivityHistory::class.java))
        }
    }


    fun logout(c: Activity, message: String) {
            val builder = AlertDialog.Builder(c)
            builder
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.ok)){ dialog, _ ->
                    dialog.cancel()
                    MyApplication.isLoggedIn=false
                    MyApplication.cnm=""
                    MyApplication.cps=""
                    val mIntent = Intent(this, ActivityLogin::class.java)
                    finishAffinity()
                    startActivity(mIntent)
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()

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
                tvCalendarDob.text = AppHelper.dateFormat1.format(textDateCalendar.time)

            }
        tvCalendarDob.setOnClickListener {
            DatePickerDialog(
                this,
                textDateListener,
                textDateCalendar.get(Calendar.YEAR),
                textDateCalendar.get(Calendar.MONTH),
                textDateCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setData(){
         tvUsername.text=MyApplication.memberDetails!!.getName()
         tvCalendarDob.text=MyApplication.memberDetails!!.dateOfBirth
         etEmail.setText(MyApplication.memberDetails!!.email!!)
         if(MyApplication.memberDetails!!.mobile!!.isNotEmpty()) {
            try{etPhoneNumber.setText(MyApplication.memberDetails!!.mobile!!.split(" ")[1])}catch (e:Exception){}
            try{
                MyApplication.selectedItemDialog=MyApplication.memberDetails!!.mobile!!.split(" ")[0]
                tvCountryCode.text=MyApplication.memberDetails!!.mobile!!.split(" ")[0]
            }catch (e:Exception){}
        }

    }

    private fun checkAndSubmit(){
        if(etEmail.text.isNullOrEmpty())
            linearEmail.startAnimation(shake)
        else if(!AppHelper.isValidEmail(etEmail.text.toString()))
            linearEmail.startAnimation(shake)
        else if(tvCalendarDob.text.isEmpty())
            linearCalendar.startAnimation(shake)
        else if(etPhoneNumber.text.isNullOrEmpty())
            linearPhone.startAnimation(shake)
        else
            updateMember()
    }

    override fun onItemClicked(view: View, position: Int) {
        dialog.dismiss()
        MyApplication.selectedItemDialog=adapter.items[position].code!!
        tvCountryCode.text = adapter.items[position].code
    }

    private fun showCountries(){
        arrayCountries.clear()
        arrayCountries.addAll(Gson().fromJson(loadJSONFromAssets("countries.json"), CountryArray::class.java).countries!!)
        dialog = Dialog(this, R.style.dialogWithoutTitle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.popup_recycler)
        dialog.setCancelable(true)
        val rv: RecyclerView = dialog.findViewById(R.id.rvData)

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

    fun getMemberDetails () {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getMemberDetails(MyApplication.memberId.toString())?.enqueue(object :
                Callback<ResponseMemberDetails> {
                override fun onResponse(
                    call: Call<ResponseMemberDetails>,
                    response: Response<ResponseMemberDetails>
                ) {
                    if(response.body()!!.success=="1" && response.body()!!.members!!.size>0){
                        MyApplication.memberDetails= response.body()!!.members!![0]
                        setData()
                    }

                }
                override fun onFailure(call: Call<ResponseMemberDetails>, t: Throwable) {
                }
            })
    }


    fun updateMember () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.updateMember(MyApplication.memberId.toString(),tvCalendarDob.text.toString(),tvCountryCode.text.toString().replace("+","")+" "+etPhoneNumber.text.toString(),etEmail.text.toString())?.enqueue(object :
                Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1"){
                        toastt(getString(R.string.profile_info_sent))
                        linearDisplayedData.show()
                        linearDataEdit.hide()
                        btEditUpdate.text = getString(R.string.edit_information)
                        getMemberDetails()

                    }else
                        toastt(getString(R.string.try_again))

                }
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    loading.hide()
                    toastt(getString(R.string.try_again))
                }
            })
    }
}