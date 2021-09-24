package com.ids.mercury.controller.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.model.response.Child
import com.ids.mercury.model.response.Menu
import com.ids.mercury.utils.hide
import com.ids.mercury.utils.setHtmlText
import com.ids.mercury.utils.show
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

internal class AdapterInsidePager(private val context: Context, private val arrayList: ArrayList<Menu>, lifecycle: Lifecycle,private var sfm: FragmentManager) :
    PagerAdapter(),RVOnItemClickListener {
    var lifecycle=lifecycle
    var supportFragmentManager=sfm
    lateinit var myYouTubePlayer:YouTubePlayer
    private val layoutInflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view:View
        var item=arrayList[position]
        view = layoutInflater.inflate(R.layout.item_csr_pager, container, false)
        val vpMedias: ViewPager = view.findViewById(R.id.vpMedias) as ViewPager
        val tbMedia: TabLayout = view.findViewById(R.id.tbMedia) as TabLayout
        val tvSummary: TextView = view.findViewById(R.id.tvSummary) as TextView
        val rlPagerTop: RelativeLayout = view.findViewById(R.id.rlPagerTop) as RelativeLayout


        tvSummary.setHtmlText(item.summary!!)
        if(item.mediaFiles!!.size > 0){
        rlPagerTop.show()
        var adapterPager = AdapterMediaPager(context,item.mediaFiles!!,lifecycle,supportFragmentManager)
        vpMedias.adapter = adapterPager
        tbMedia.setupWithViewPager(vpMedias)
        vpMedias.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                try{adapterPager.stopPlayer()}catch (e:Exception){}
            }

        })
        }else{
            rlPagerTop.hide()
        }


        container.addView(view)
        return view
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun onItemClicked(view: View, position: Int) {

    }

    fun stopPlayer(){
        myYouTubePlayer.pause()
    }




}