package com.ids.mercury.controller.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterClasses
import com.ids.mercury.controller.Adapters.AdapterGymPackages
import com.ids.mercury.controller.Adapters.AdapterMediaPager
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.MediaFile
import com.ids.mercury.model.response.ResponseMenus
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.activity_classes.*
import kotlinx.android.synthetic.main.overlay.*
import kotlinx.android.synthetic.main.toolbar.*


class ActivityClasses : AppCompactBase(),RVOnItemClickListener,MenusDataListener,ApiListener {

    private var arrayMediaPager=java.util.ArrayList<MediaFile>()
    private lateinit var adapterPager: AdapterMediaPager
    lateinit var adapterClasses : AdapterClasses
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classes)
        init()
        GetMenusAPI.getMenus(this,AppConstants.MENU_GYM_CLASSES_LABEL,-1,0,this)
        listeners()
        if(MyApplication.arraySuggestedClasses.size>0)
            setSuggestedData()
        else
            CallApi.getSuggestedClasses(this,this)

    }


    @SuppressLint("ResourceType")
    private fun init(){
        btProfile.show()
        tvToolbarTitle.text=getString(R.string.fitness_classes)
        AppHelper.setToolbarScrollAnimation(this, linearToolbar, tvToolbarTitle, myScroll, tvTitleOver)


    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{startActivity(Intent(this,ActivityProfile::class.java))}
        linearSuggestedClasses.setOnClickListener{0
            startActivity(Intent(this,ActivitySuggestedClasses::class.java).putExtra(AppConstants.FROM_PAGE,AppConstants.PAGE_CLASSES))
        }

        btRegister.setOnClickListener{
            startActivity(Intent(this,ActivityRenewMembership::class.java).putExtra("type",AppConstants.TYPE_GYM))
        }
    }


    override fun onItemClicked(view: View, position: Int) {
        startActivity(Intent(this,ActivityInsideClasses::class.java).putExtra(AppConstants.SESSION_ID,
            adapterClasses.items[position].id!!))
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
        if(responseMenus.menus!!.size>0){
          arrayMediaPager.addAll(responseMenus.menus!![0].mediaFiles!!)
          setPager()
        }else{
           llShading.setBackgroundResource(R.drawable.rectangular_gray)
        }


        tvTitleOver.text=responseMenus.menus!![0].name!!
        tvSummary.setHtmlText(responseMenus.menus!![0].summary!!)

    }


    private fun setSuggestedData(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvSuggested.layoutManager = layoutManager
        adapterClasses = AdapterClasses(MyApplication.arraySuggestedClasses,this)
        rvSuggested.adapter = adapterClasses
    }

    override fun onDataRetrieved(success: Boolean, response: Any) {
        setSuggestedData()
    }

}