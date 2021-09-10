package com.ids.mercury.controller.Adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.SectionPagerItem
import com.ids.mercury.utils.show

import java.util.*


class AdapterItemSections(
    val items: ArrayList<SectionPagerItem>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterItemSections.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_pager_recycler_section, parent, false)
        val lp = v.layoutParams as GridLayoutManager.LayoutParams
        lp.height = parent.measuredHeight / (MyApplication.pageItemCount/MyApplication.gridRecyclerCount)
        v.layoutParams = lp
        return VHItem(v)
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        var item=items[position]
        holder.tvTitle.text = items[position].title
        if(item.localImage!!)
           holder.ivItem.setImageResource(item.imageLocal!!)
        else{
            //Load url
        }


        holder.layer.show()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var layer = itemView.findViewById<LinearLayout>(R.id.layer)
        var ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}