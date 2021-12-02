package com.ids.mercury.controller.Activities


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.viewpager.widget.ViewPager
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterClasses
import com.ids.mercury.controller.Adapters.AdapterGridPager
import com.ids.mercury.controller.Adapters.AdapterMediaPager
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.PagerSectionArray
import com.ids.mercury.model.SectionPagerItem
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_academy.*
import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.overlay.*

import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class ActivityTrainers : AppCompactBase(),RVOnItemClickListener,MenusDataListener,ApiListener {

    private var arrayMediaPager=java.util.ArrayList<MediaFile>()
    private lateinit var adapterPager: AdapterMediaPager
    lateinit var adapterClasses : AdapterClasses
    var pageItemCount=4

    private var arrayPagesSections=java.util.ArrayList<PagerSectionArray>()
    private lateinit var adapterPagerAcadmies: AdapterGridPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_academy)
        init()
      //  GetMenusAPI.getMenus(this,AppConstants.MENU_ACADEMIES_LABEL,-1,0,this)
        listeners()
        getTrainers()
        setTopData()

    }


    @SuppressLint("ResourceType")
    private fun init(){
        btProfile.show()
        tvToolbarTitle.text=getString(R.string.pts)
        AppHelper.setToolbarScrollAnimation(this, linearToolbar, tvToolbarTitle, myScroll, tvTitleOver)

    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{startActivity(Intent(this,ActivityProfile::class.java))}
    }


    override fun onItemClicked(view: View, position: Int) {
        startActivity(Intent(this,ActivityInsideClasses::class.java).putExtra(AppConstants.SESSION_ID,
            adapterClasses.items[position].id!!))
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

    override fun onDataMenuRetrieved(success: Boolean, responseMenus: ResponseMenus) {
        arrayMediaPager.clear()
        if(responseMenus.menus!!.size>0){
            if(responseMenus.menus!![0].mediaFiles!!.size>0){
                arrayMediaPager.addAll(responseMenus.menus!![0].mediaFiles!!)
                setPager()
            }
            else{
                llShading.setBackgroundResource(R.drawable.rectangular_gray)
            }


            tvTitleOver.text=responseMenus.menus!![0].name!!
            setPager()
        }
    }

    private fun setTopData(){
        arrayMediaPager.clear()
        arrayMediaPager.add(MediaFile(1,"",1,"","","","",false,false,true,R.drawable.trainers_bg))
        tvTitleOver.text=getString(R.string.pts)
        setPager()
    }



    fun getTrainers() {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getCoaches(2)?.enqueue(object :
                Callback<ResponseCoaches> {
                override fun onResponse(
                    call: Call<ResponseCoaches>,
                    response: Response<ResponseCoaches>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1" && response.body()!!.coaches!!.size>0){
                        tvNodata.hide()
                        setPagerCoaches(response.body()!!.coaches!!)
                    }else
                        tvNodata.show()

                }

                override fun onFailure(call: Call<ResponseCoaches>, t: Throwable) {
                    loading.hide()
                    tvNodata.show()
                }
            })
    }


    private fun setPagerCoaches(activities: ArrayList<Coach>) {
        arrayPagesSections.clear()
        var arraySorted=activities.sortedBy { it.name }
        arrayPagesSections.addAll(setCoachesData(this,ArrayList(arraySorted)))
        adapterPagerAcadmies = AdapterGridPager(this,arrayPagesSections)
        vpAcademy.adapter = adapterPagerAcadmies
        tbAcademy.setupWithViewPager(vpAcademy)
        vpAcademy?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
            }

        })

    }

    private fun setCoachesData(context:Context,coaches: ArrayList<Coach>):ArrayList<PagerSectionArray> {
        var arrayPagesSections=java.util.ArrayList<PagerSectionArray>()
        var arrayAllSections=java.util.ArrayList<SectionPagerItem>()

        for (i in coaches.indices){
            arrayAllSections.add(SectionPagerItem(
                AppConstants.MENU_TRAINERS,
                coaches[i].name,
                "",
                R.drawable.rectangular_gray,
                if(coaches[i].mediaFiles!=null && coaches[i].mediaFiles!!.size >0) coaches[i].mediaFiles!![0].filePath else "",
                true,
                false
            )
            {
                MyApplication.selectedCoach=coaches[i]
                context.startActivity(
                    Intent(context, ActivityTrainersDetails::class.java)
                )})
        }


        var tempArray=java.util.ArrayList<SectionPagerItem>()
        arrayPagesSections.clear()
        for (i in arrayAllSections.indices){
            tempArray.add(arrayAllSections[i])
            if((((i+1) % pageItemCount) == 0 ) && i!=0 || (i == arrayAllSections.size-1)){
                arrayPagesSections.add(PagerSectionArray(tempArray))
                tempArray= arrayListOf()
            }
        }

        return arrayPagesSections
    }

    override fun onDataRetrieved(success: Boolean, response: Any) {

    }


}