package com.ids.mercury.controller.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterCoaches
import com.ids.mercury.controller.Adapters.AdapterMediaPager
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.Coach
import com.ids.mercury.model.response.MediaFile
import com.ids.mercury.model.response.ResponseCoaches
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_academy_details.*

import kotlinx.android.synthetic.main.overlay.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActivityAcademyDetails : AppCompactBase(),RVOnItemClickListener{

    private var arrayMediaPager=java.util.ArrayList<MediaFile>()
    private lateinit var adapterPager: AdapterMediaPager

    private var arrayCoaches=java.util.ArrayList<Coach>()
    private var arrayAssistants=java.util.ArrayList<Coach>()
    lateinit var adapterCoaches : AdapterCoaches
    lateinit var adapterAssistants : AdapterCoaches
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_academy_details)
        init()
        listeners()
        getCoaches()
    }


    @SuppressLint("ResourceType")
    private fun init(){
        btProfile.show()
        try{tvToolbarTitle.text=MyApplication.selectedAcademy!!.name!!}catch (e:Exception){}
        AppHelper.setToolbarScrollAnimation(this, linearToolbar, tvToolbarTitle, myScroll, tvTitleOver)

        Handler(Looper.getMainLooper()).postDelayed({
            setPagerTopData()
        }, 300)
    }
    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{startActivity(Intent(this,ActivityProfile::class.java))}
        linearFullSchedule.setOnClickListener{
            startActivity(Intent(this,ActivitySuggestedClasses::class.java).putExtra(AppConstants.FROM_PAGE,AppConstants.PAGE_ACADEMY))
        }

        btSubmit.setOnClickListener{
            startActivity(Intent(this,ActivitySuccess::class.java))
        }
    }


    override fun onItemClicked(view: View, position: Int) {
        startActivity(Intent(this,ActivityInsideClasses::class.java).putExtra(AppConstants.SESSION_ID,
            1))
    }

    private fun setPager(){
        adapterPager = AdapterMediaPager(this,arrayMediaPager,lifecycle)
        vpMedias.adapter = adapterPager
        tbMedia.setupWithViewPager(vpMedias)
        vpMedias?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                try{adapterPager.stopPlayer()}catch (e:Exception){}
            }

        })

    }


    override fun onStop() {
        linearToolbar.background.alpha = 255
        super.onStop()
    }


    private fun setPagerTopData(){
        arrayMediaPager.clear()
        if(MyApplication.selectedAcademy!!.imageURL!=null && !MyApplication.selectedAcademy!!.imageURL.isNullOrEmpty()){
            arrayMediaPager.add(MediaFile(1,"",1,"","",MyApplication.selectedAcademy!!.imageURL!!,"",false,false))
            setPager()
        }else{
            llShading.setBackgroundResource(R.drawable.rectangular_gray)
        }
        try{tvTitleOver.text=MyApplication.selectedAcademy!!.name!!}catch (e:Exception){}
        try{tvSummary.setHtmlText(MyApplication.selectedAcademy!!.description!!)}catch (e:java.lang.Exception){}
    }



    fun getCoaches () {
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getAcademyCoaches(MyApplication.selectedAcademy!!.id!!)?.enqueue(object :
                Callback<ResponseCoaches> {
                override fun onResponse(
                    call: Call<ResponseCoaches>,
                    response: Response<ResponseCoaches>
                ) {
                    if(response.body()!!.success=="1" && response.body()!!.coaches!!.size>0){
                        setData(response.body()!!.coaches!!)
                    }else{
                        noDataSet()
                    }

                }
                override fun onFailure(call: Call<ResponseCoaches>, t: Throwable) {
                    noDataSet()
                }
            })
    }


    private fun noDataSet(){
        tvNoCoachData.show()
        tvNoAssistantData.show()
        rvCoaches.hide()
        rvAssistants.hide()
    }

    private fun setData(coaches: ArrayList<Coach>) {
        arrayCoaches.clear()
        arrayAssistants.clear()
        arrayCoaches.addAll(coaches.filter { it.coachType==1 })
        arrayAssistants.addAll(coaches.filter { it.coachType==2 })
        if(arrayCoaches.size>0)
            setCoachesData()
        else{
            tvNoCoachData.show()
            rvCoaches.hide()
        }

        if(arrayAssistants.size>0)
            setAssistantsData()
        else{
            tvNoAssistantData.show()
            rvAssistants.hide()
        }
    }

    private fun setCoachesData(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvCoaches.layoutManager = layoutManager
        adapterCoaches = AdapterCoaches(arrayCoaches,this)
        rvCoaches.adapter = adapterCoaches
    }


    private fun setAssistantsData(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvAssistants.layoutManager = layoutManager
        adapterAssistants = AdapterCoaches(arrayAssistants,this)
        rvAssistants.adapter = adapterAssistants
    }

}