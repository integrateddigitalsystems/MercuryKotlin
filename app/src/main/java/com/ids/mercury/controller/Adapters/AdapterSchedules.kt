package com.ids.mercury.controller.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnSubItemClickListener
import com.ids.mercury.model.response.ScheduleArray


import java.util.ArrayList

class AdapterSchedules(
    val items: ArrayList<ScheduleArray>,
    private val itemClickListener: RVOnSubItemClickListener
) :
    RecyclerView.Adapter<AdapterSchedules.VHItem>(),RVOnSubItemClickListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        return VHItem(LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_header, parent, false))
    }


    override fun onBindViewHolder(holder: VHItem, position: Int) {
        try{holder.tvTitle.text = items[position].day}catch (e:Exception){}

        val layoutManager = LinearLayoutManager(holder.itemView.context, RecyclerView.VERTICAL, false)
        holder.rvScheduleInside.layoutManager = layoutManager
        var adapterClasses = AdapterScheduleSection(items[position].arraySchedule!!,this,position)
        holder.rvScheduleInside.adapter = adapterClasses
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvTitle = itemView.findViewById<TextView>(R.id.tvHeader)
        var rvScheduleInside = itemView.findViewById<RecyclerView>(R.id.rvScheduleInside)

        init {
           // itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
          //  itemClickListener.onItemClicked(v, layoutPosition)
        }
    }



    override fun onSubItemClicked(view: View, position: Int, parentPosition: Int) {
        itemClickListener.onSubItemClicked(view,position,parentPosition)
    }
}