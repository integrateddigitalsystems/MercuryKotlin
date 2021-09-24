package com.ids.mercury.controller.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.AdapterInsidePager
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*
import kotlinx.android.synthetic.main.acitivity_inside_csr.*

import kotlinx.android.synthetic.main.toolbar.*



class ActivityInsideCsrRules : AppCompactBase(),RVOnItemClickListener,MenusDataListener,ApiListener {

    private var arrayMenus=java.util.ArrayList<Menu>()
    private lateinit var adapterPager: AdapterInsidePager
    var type=1
    var selectedParentId=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_inside_csr)
        init()
    }

    private fun init(){
        btProfile.show()
        type=intent.getIntExtra("type",1)
        if(type == AppConstants.TYPE_CSR){
            selectedParentId=MyApplication.selectedCsr!!.id!!
            GetMenusAPI.getMenus(this,"",-1,MyApplication.selectedCsr!!.id!!,this)
        }else{
            GetMenusAPI.getMenus(this,AppConstants.MENU_RULES_LABEL,-1,0,this)
        }
        listeners()
    }

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{startActivity(Intent(this,ActivityProfile::class.java))}
    }


    override fun onItemClicked(view: View, position: Int) {
        /*   startActivity(Intent(this,ActivityInsideClasses::class.java).putExtra(AppConstants.SESSION_ID,
               adapterClasses.items[position].id!!))*/
    }

    private fun setPager(){

        adapterPager = AdapterInsidePager(this,arrayMenus,lifecycle,supportFragmentManager)
        vpCsrInside.adapter = adapterPager
        tbCsr.setupWithViewPager(vpCsrInside)
        vpCsrInside?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

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
        arrayMenus.clear()
        if(responseMenus.menus!!.size>0){
            if(responseMenus.menus!!.size>1)
                tbCsr.show()
            else
                tbCsr.hide()

            arrayMenus.addAll(responseMenus.menus!!)
              setPager()
        }
    }


    override fun onDataRetrieved(success: Boolean, response: Any) {

    }

}