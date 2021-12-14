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
import com.ids.mercury.model.response.ResponseMessage
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_academy_details.*
import kotlinx.android.synthetic.main.activity_academy_details.myScroll
import kotlinx.android.synthetic.main.activity_academy_details.tbMedia
import kotlinx.android.synthetic.main.activity_academy_details.tvSummary
import kotlinx.android.synthetic.main.activity_academy_details.tvTitleOver
import kotlinx.android.synthetic.main.activity_academy_details.vpMedias
import kotlinx.android.synthetic.main.activity_trainers_details.*

import kotlinx.android.synthetic.main.overlay.*
import kotlinx.android.synthetic.main.toolbar.*



class ActivityTrainersDetails : AppCompactBase(),RVOnItemClickListener{

    private var arrayMediaPager=java.util.ArrayList<MediaFile>()
    private lateinit var adapterPager: AdapterMediaPager

    private var arrayCoaches=java.util.ArrayList<Coach>()
    private var arrayAssistants=java.util.ArrayList<Coach>()
    lateinit var adapterCoaches : AdapterCoaches
    lateinit var adapterAssistants : AdapterCoaches
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainers_details)
        init()
        listeners()
    }


    @SuppressLint("ResourceType")
    private fun init(){
        btProfile.show()
        try{tvToolbarTitle.text=MyApplication.selectedCoach!!.name!!}catch (e:Exception){}
        AppHelper.setToolbarScrollAnimation(this, linearToolbar, tvToolbarTitle, myScroll, tvTitleOver)

        Handler(Looper.getMainLooper()).postDelayed({
            setPagerTopData()
        }, 300)

        setCoachData()
    }
    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{startActivity(Intent(this,ActivityProfile::class.java))}


    }


    override fun onItemClicked(view: View, position: Int) {
        startActivity(Intent(this,ActivityInsideClasses::class.java).putExtra(AppConstants.SESSION_ID,
            1))
    }

    private fun setPager(){
        adapterPager = AdapterMediaPager(this,arrayMediaPager,lifecycle,supportFragmentManager)
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
        if(MyApplication.selectedCoach!!.mediaFiles!=null && MyApplication.selectedCoach!!.mediaFiles!!.size>0){
            var arrayMedia=MyApplication.selectedCoach!!.mediaFiles!!
            for (i in arrayMedia.indices)
                arrayMediaPager.add(MediaFile(1,"",1,"","",arrayMedia[i].filePath,"",false,false))
            setPager()
        }else{
            llShading.setBackgroundResource(R.drawable.rectangular_gray)
        }
        try{tvTitleOver.text=MyApplication.selectedCoach!!.name!!}catch (e:Exception){}
        try{tvSummary.setHtmlText(MyApplication.selectedCoach!!.summary!!)}catch (e:java.lang.Exception){}
    }




  private fun setCoachData(){
      if(MyApplication.selectedCoach!!.department!=null)
         tvDepartmentValue.text=MyApplication.selectedCoach!!.department.toString()

      if(!MyApplication.selectedCoach!!.email.isNullOrEmpty())
          tvEmailValue.text=MyApplication.selectedCoach!!.email.toString()

      if(!MyApplication.selectedCoach!!.mobile.isNullOrEmpty())
          tvPhoneValue.text=MyApplication.selectedCoach!!.mobile.toString()
  }




}