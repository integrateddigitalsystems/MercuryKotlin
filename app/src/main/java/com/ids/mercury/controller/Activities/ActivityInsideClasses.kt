package com.ids.mercury.controller.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterMediaPager
import com.ids.mercury.controller.Adapters.AdapterSchedules
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*
import com.ids.mercury.utils.AppHelper.Companion.setToolbarScrollAnimation
import kotlinx.android.synthetic.main.activity_classes.vpMedias
import kotlinx.android.synthetic.main.activity_inside_classes.*
import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.overlay.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ActivityInsideClasses : AppCompactBase() {

    private var arrayMediaPager=java.util.ArrayList<MediaFile>()
    private lateinit var adapterPager: AdapterMediaPager
    lateinit var adapterSchedules: AdapterSchedules
    var arraySchedule=java.util.ArrayList<ScheduleArray>()
    var sessionId=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_classes)
        init()
        listeners()
        getGymClassesBySession()

    }


    @SuppressLint("ResourceType")
    private fun init(){
        btProfile.show()
        setToolbarScrollAnimation(this,linearToolbar,tvToolbarTitle,myScroll,tvTitleOver)
        sessionId=intent.getIntExtra(AppConstants.SESSION_ID,0)
    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{startActivity(Intent(this,ActivityProfile::class.java))}
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





    fun getGymClassesBySession () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getGymClassDetailsBySession(sessionId)?.enqueue(object :
                Callback<ResponseGymClasses> {
                override fun onResponse(
                    call: Call<ResponseGymClasses>,
                    response: Response<ResponseGymClasses>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1" && response.body()!!.gymClasses!!.size>0)
                        setSessionData(response.body()!!.gymClasses)


                }
                override fun onFailure(call: Call<ResponseGymClasses>, t: Throwable) {
                    loading.hide()

                }
            })
    }





    private fun setSessionData(gymClasses: ArrayList<GymClass>?) {
        var data=gymClasses!![0]
       try{ tvDate.text=AppHelper.formatDateTimestamp(AppHelper.dateFormat7, data.fromDate!!,true)!!}catch (e:Exception){}

        try{
            @SuppressLint("SetTextI18n")
            tvDTime.text=AppHelper.formatDateTimestamp(AppHelper.dateFormat5,data.fromDate!!,true)+" - "+
                AppHelper.formatDateTimestamp(AppHelper.dateFormat5,data.toDate!!,true)}catch (e:Exception){}
        if(!data.level.isNullOrEmpty())
          tvLevel.text=data.level!!
        if(!data.details.isNullOrEmpty())
           tvAbout.text=data.details
        if(!data.coachName.isNullOrEmpty())
            tvTrainer.text=data.coachName
        if(!data.place.isNullOrEmpty())
            tvPlace.text=data.place

        if(!data.name.isNullOrEmpty()){
            tvToolbarTitle.text=data.name
            tvTitleOver.text=data.name
        }

        arrayMediaPager.clear()
        if(data.mediaFiles!!.size>0){
            arrayMediaPager.addAll(data.mediaFiles!!)
             setPager()
        }
        else{
            llShading.setBackgroundResource(R.drawable.rectangular_gray)
        }

    }




}