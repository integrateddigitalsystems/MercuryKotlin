package com.ids.mercury.controller.Activities


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterMediaPager
import com.ids.mercury.controller.Adapters.AdapterSchedules
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnSubItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*
import com.ids.mercury.utils.AppHelper.Companion.dateFormat1
import kotlinx.android.synthetic.main.activity_classes.myScroll
import kotlinx.android.synthetic.main.activity_classes.rvSuggested
import kotlinx.android.synthetic.main.activity_classes.tbMedia
import kotlinx.android.synthetic.main.activity_classes.tvTitleOver
import kotlinx.android.synthetic.main.activity_classes.vpMedias
import kotlinx.android.synthetic.main.activity_loyality_points.*
import kotlinx.android.synthetic.main.overlay.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ActivitySuggestedClasses : AppCompactBase(),RVOnSubItemClickListener,MenusDataListener,ApiListener {

    private var arrayMediaPager=java.util.ArrayList<MediaFile>()
    private lateinit var adapterPager: AdapterMediaPager
    lateinit var adapterSchedules: AdapterSchedules
    var arraySchedule=java.util.ArrayList<ScheduleArray>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggested_classes)
        init()
        GetMenusAPI.getMenus(this,AppConstants.MENU_CLASS_SCHEDULE_LABEL,-1,0,this)
        listeners()
        getSchedules()

    }


    @SuppressLint("ResourceType")
    private fun init(){
        btProfile.show()
        AppHelper.setToolbarScrollAnimation(this, linearToolbar, tvToolbarTitle, myScroll, tvTitleOver)
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

    override fun onDataMenuRetrieved(success: Boolean, responseMenus: ResponseMenus) {
        arrayMediaPager.clear()
        if(responseMenus.menus!!.size>0)
            arrayMediaPager.addAll(responseMenus.menus!![0].mediaFiles!!)

        tvTitleOver.setHtmlText(responseMenus.menus!![0].summary!!)
        tvToolbarTitle.setHtmlText(responseMenus.menus!![0].summary!!)
        if(arrayMediaPager.size>0)
          setPager()
        else{
            llShading.setBackgroundResource(R.drawable.rectangular_gray)
        }
    }



    fun getSchedules () {
        var fromDate = dateFormat1.format(Date())
        var toDate = dateFormat1.format(Date(Date().time+604800000L) )
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getGymSchedule(fromDate,toDate)?.enqueue(object :
                Callback<ResponseClassSessions> {
                override fun onResponse(
                    call: Call<ResponseClassSessions>,
                    response: Response<ResponseClassSessions>
                ) {
                    if(response.body()!!.success=="1" && response.body()!!.classSessions!!.size>0){
                        tvNodata.hide()
                        setSchedules(response.body()!!.classSessions)
                    }else
                        tvNodata.show()

                }
                override fun onFailure(call: Call<ResponseClassSessions>, t: Throwable) {
                    tvNodata.show()
                }
            })
    }


    private fun setSchedules(classSessions: ArrayList<ClassSession>?) {
        setArrayDataSchedule(classSessions)
        setData()
    }

   private fun setArrayDataSchedule(classSessions: ArrayList<ClassSession>?) {
       arraySchedule.clear()
       var grouped =classSessions!!.groupBy { AppHelper.formatDateTimestamp(AppHelper.dateFormat6,it.fromDate!!,true) }
       for ((key, value) in grouped)
           arraySchedule.add(ScheduleArray(key,ArrayList(value)))
   }

    private fun setData(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvSuggested.layoutManager = layoutManager
        adapterSchedules = AdapterSchedules(arraySchedule,this)
        rvSuggested.adapter = adapterSchedules
    }

    override fun onDataRetrieved(success: Boolean, response: Any) {

    }

    override fun onSubItemClicked(view: View, position: Int, parentPosition: Int) {
       startActivity(Intent(this,ActivityInsideClasses::class.java).putExtra(AppConstants.SESSION_ID,
           adapterSchedules.items[parentPosition].arraySchedule!![position].id!!))
    }

}