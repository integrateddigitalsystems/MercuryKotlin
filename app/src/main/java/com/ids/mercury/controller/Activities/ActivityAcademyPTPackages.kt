package com.ids.mercury.controller.Activities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.*
import com.ids.mercury.controller.Adapters.AdapterMediaPager
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Base.AppCompactBase
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.*
import com.ids.mercury.utils.*
import com.ids.mercury.utils.PaymentApiDialog.Companion.dialog
import kotlinx.android.synthetic.main.activity_academy.*
import kotlinx.android.synthetic.main.activity_academy_details.*
import kotlinx.android.synthetic.main.activity_academy_ptpackages.*
import kotlinx.android.synthetic.main.activity_loyality_points.*
import kotlinx.android.synthetic.main.activity_rent_court.*
import kotlinx.android.synthetic.main.item_academy_pt_package.*
import kotlinx.android.synthetic.main.item_academy_pt_package.tvName
import kotlinx.android.synthetic.main.item_academy_pt_package.view.*
import kotlinx.android.synthetic.main.item_popup.*
import kotlinx.android.synthetic.main.item_popup_academy_pt_packages.*
import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.overlay.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  ActivityAcademyPTPackages :AppCompactBase(),RVOnItemClickListener,ApiListener {
    private var arrayMediaPager=java.util.ArrayList<MediaFile>()

    private lateinit var adapterPager: AdapterMediaPager
    private var arrayAcademyPtPackage=java.util.ArrayList<AcademyPtPackage>()
    lateinit var adapter : AdapterAcademyPTPackages

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_academy_ptpackages)
        init()
        listeners()
        getAcademyPTPackageData()

    }

    private fun init(){
        btProfile.show()
        tvToolbarTitle.text=getString(R.string.academy_pt_packages)
        tvTitleOverPt.text=getString(R.string.academy_pt_packages)
        AppHelper.setToolbarScrollAnimation(this, linearToolbar, tvToolbarTitle, myScrollPt, tvTitleOverPt)
        arrayMediaPager.add(MediaFile(13,null,R.drawable.academy_pt_package,true))
        setPager()

    }

    private fun setPager(){
        adapterPager = AdapterMediaPager(this,arrayMediaPager,lifecycle,supportFragmentManager)
        vpMediasPt.adapter = adapterPager
        tbMediaPt.setupWithViewPager(vpMediasPt)
        vpMediasPt?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

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

    private fun listeners(){
        btBack.setOnClickListener{super.onBackPressed()}
        btProfile.setOnClickListener{startActivity(Intent(this,ActivityProfile::class.java))}
    }

    override fun onItemClicked(view: View, position: Int) {
        if (view.id == R.id.ivShow){
            clickImageCheck(position)
        }else{
            showItem(position)
        }
    }
    fun clickImageCheck(position:Int){
        arrayAcademyPtPackage[position].isMultiUser = !arrayAcademyPtPackage[position].isMultiUser!!
        adapter.notifyItemChanged(position)
    }

    fun showItem(position:Int){
        val isChecked = adapter.items[position].isMultiUser!!
        dialog = Dialog(this, R.style.dialogAcademyPtPackages)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.item_popup_academy_pt_packages)
        dialog.tvName.text = adapter.items[position].name
        dialog.tvAmountPt.text = adapter.items[position].amount.toString()
        dialog.ivShowPt.setImageResource(if(isChecked) R.drawable.icon_true else R.drawable.icon_false)
        dialog.setCancelable(true)
        dialog.show()
    }
    fun getAcademyPTPackageData () {
        loading.show()
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.getAcademyPTPackages()?.enqueue(object :
                Callback<ResponseAcademyPtPackages> {
                override fun onResponse(
                    call: Call<ResponseAcademyPtPackages>,
                    response: Response<ResponseAcademyPtPackages>
                ) {
                    loading.hide()
                    if(response.body()!!.success=="1" && response.body()!!.academyPTPackages!!.size>0){
                        setData(response.body()!!.academyPTPackages!!)
                    }

                }
                override fun onFailure(call: Call<ResponseAcademyPtPackages>, t: Throwable) {
                    loading.hide()
                    toastt(getString(R.string.try_again))
                }
            })
    }

    private fun setData(history: ArrayList<AcademyPtPackage>?) {
        arrayAcademyPtPackage.clear()
        arrayAcademyPtPackage.addAll(history!!)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvPtPackage.layoutManager = layoutManager
        adapter = AdapterAcademyPTPackages(arrayAcademyPtPackage,this)
        rvPtPackage.adapter = adapter

    }

    override fun onDataRetrieved(success: Boolean, response: Any) {
    }


}



