package com.ids.mercury.controller.Fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ids.mercury.R
import com.ids.mercury.controller.Activities.ActivityLogin
import com.ids.mercury.controller.Adapters.AdapterCountryCodes
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.CountryArray
import com.ids.mercury.model.CountryCodes
import com.ids.mercury.model.response.ResponseMessage
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.fragment_forget.*
import kotlinx.android.synthetic.main.fragment_set_password.*
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class FragmentSignUp : Fragment() , RVOnItemClickListener {

    var canClick=true
    lateinit var  shake: Animation
    private var arrayCountries=java.util.ArrayList<CountryCodes>()
    lateinit var adapter : AdapterCountryCodes
    lateinit var dialog :Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_signup, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listeners()

    }

    private fun init(){
        shake =  AnimationUtils.loadAnimation(requireActivity(), R.anim.shake)

    }

    private fun listeners(){
        btBack.setOnClickListener{
            (activity as ActivityLogin).backClick()
        }

        btSignUp.setOnClickListener{
            checkAndSubmit()
        }

        tvCountryCode.setOnClickListener{
            showCountires()
        }
        setDatesPicker()
    }

    private fun showCountires(){
        arrayCountries.clear()
        arrayCountries.addAll(Gson().fromJson(loadJSONFromAssets("countries.json"), CountryArray::class.java).countries!!)
        dialog = Dialog(requireActivity(), R.style.dialogWithoutTitle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.popup_country_codes)
        dialog.setCancelable(true)
        val rv: RecyclerView = dialog.findViewById(R.id.rvCountryCodes)

        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rv.layoutManager = layoutManager
        adapter = AdapterCountryCodes(arrayCountries,this)
        rv.adapter = adapter

        try{
        var item=arrayCountries.find { it.code!!.replace("+","").trim()==MyApplication.selectedItemDialog.replace("+","").trim() }
        var position=arrayCountries.indexOf(item!!)
        rv.scrollToPosition(position)}catch (e:Exception){}

        dialog.show()
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
                tvDob.text = AppHelper.dateFormat1.format(textDateCalendar.time)

            }
        tvDob.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                textDateListener,
                textDateCalendar.get(Calendar.YEAR),
                textDateCalendar.get(Calendar.MONTH),
                textDateCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun checkAndSubmit(){
        when {
            etFirstName.text.isNullOrEmpty() -> etFirstName.startAnimation(shake)
            etLastName.text.isNullOrEmpty() -> etLastName.startAnimation(shake)
            etPhoneNumber.text.isNullOrEmpty() -> linearPhone.startAnimation(shake)
            tvDob.text.isNullOrEmpty() -> tvDob.startAnimation(shake)
            etEmail.text.isNullOrEmpty() -> etEmail.startAnimation(shake)
            !AppHelper.isValidEmail(etEmail.text.toString()) -> etEmail.startAnimation(shake)
            else -> submitSignUp()
        }
    }

    override fun onResume() {
        super.onResume()
        canClick=true
    }

    override fun onItemClicked(view: View, position: Int) {
        dialog.dismiss()
        MyApplication.selectedItemDialog=adapter.items[position].code!!
        tvCountryCode.text = adapter.items[position].code
    }


    private fun submitSignUp(){
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.saveMember(
                etFirstName.text.toString(),
                "",
                etLastName.text.toString(),
                tvCountryCode.text.toString().replace("+","")+" "+etPhoneNumber.text.toString(),
                tvDob.text.toString(),
                etEmail.text.toString()

            )?.enqueue(object :
                Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1"){
                      MyApplication.tempMemberId=response.body()!!.id.toString()
                      MyApplication.verificationTitle=getString(R.string.sign_up)
                      (activity as ActivityLogin).goToVerification()
                    }else
                        AppHelper.createDialog(requireActivity(),response.body()!!.message!!)

                }
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    loading.hide()
                    toastt(getString(R.string.try_again))
                }
            })
    }

}

















