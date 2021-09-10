package com.ids.mercury.controller.Adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.PagerSectionArray



internal class AdapterGridPager(private val context: Context, private val arrayList: ArrayList<PagerSectionArray>) :
    PagerAdapter(),RVOnItemClickListener {
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
        view = layoutInflater.inflate(R.layout.item_home_pager, container, false)
        val rvPagerSections: RecyclerView = view.findViewById(R.id.rvPagerSections) as RecyclerView


        //set recycler
        val adapterSections=AdapterItemSections(item.arraySections!!,this)
        rvPagerSections.adapter = adapterSections
        var glm = GridLayoutManager(context, MyApplication.gridRecyclerCount)
        rvPagerSections.layoutManager = glm
        rvPagerSections.isNestedScrollingEnabled = true

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


}