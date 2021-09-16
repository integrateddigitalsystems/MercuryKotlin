package com.ids.mercury.controller.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterGridPager
import com.ids.mercury.controller.Adapters.AdapterItemSections
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.Fragments.*
import com.ids.mercury.controller.MyApplication.Companion.pageItemCount
import com.ids.mercury.model.CountryCodes
import com.ids.mercury.model.PagerSectionArray
import com.ids.mercury.model.SectionPagerItem
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.acitivity_home.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.toolbar.*


class ActivityHome : AppCompactBase() {
    private var arrayPagesSections=java.util.ArrayList<PagerSectionArray>()
    private lateinit var adapterPager: AdapterGridPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_home)
        init()
        listeners()
    }

    private fun init(){
       btBack.hide()
       btProfile.show()
       btNotification.show()
       setPager()

    }

    private fun listeners(){
        btNotification.setOnClickListener{
            startActivity(Intent(this,ActivityNotifications::class.java))
        }

        btProfile.setOnClickListener{
            startActivity(Intent(this,ActivityProfile::class.java))
        }
    }

    private fun setPager(){
        arrayPagesSections.clear()
        arrayPagesSections.addAll(AppHelper.setPagerArray(this,pageItemCount))
        adapterPager = AdapterGridPager(this,arrayPagesSections)
        vpSections.adapter = adapterPager
        tbHome.setupWithViewPager(vpSections)
        vpSections?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
           }

        })

    }


}