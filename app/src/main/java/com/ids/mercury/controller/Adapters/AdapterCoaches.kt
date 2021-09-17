package com.ids.mercury.controller.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.Coach
import com.ids.mercury.model.response.GymClass
import com.ids.mercury.model.response.GymPackage
import com.ids.mercury.utils.AppHelper
import com.ids.mercury.utils.hide


import java.util.ArrayList

class AdapterCoaches(
    val items: ArrayList<Coach>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterCoaches.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        return VHItem(
            LayoutInflater.from(parent.context).inflate(R.layout.item_coach, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        try{holder.tvName.text = items[position].name}catch (e:Exception){}
      }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvName = itemView.findViewById<TextView>(R.id.tvName)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}