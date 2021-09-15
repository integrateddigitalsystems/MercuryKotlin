package com.ids.mercury.controller.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.GymClass
import com.ids.mercury.model.response.GymPackage
import com.ids.mercury.utils.AppHelper


import java.util.ArrayList

class AdapterClasses(
    val items: ArrayList<GymClass>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterClasses.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        return VHItem(
            LayoutInflater.from(parent.context).inflate(R.layout.item_class, parent, false)
        )
    }


    override fun onBindViewHolder(holder: VHItem, position: Int) {
        try{holder.tvTitle.text = items[position].name}catch (e:Exception){}
        try{holder.tvCoachName.text = items[position].coachName}catch (e:Exception){}

        try{
            @SuppressLint("SetTextI18n")
            holder.tvDate.text = AppHelper.formatDateTimestamp(AppHelper.dateFormat4,items[position].fromDate!!,true)+", "+
                    AppHelper.formatDateTimestamp(AppHelper.dateFormat5,items[position].fromDate!!,true)+" - "+
                    AppHelper.formatDateTimestamp(AppHelper.dateFormat5,items[position].toDate!!,true)}catch (e:Exception){}
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        var tvCoachName = itemView.findViewById<TextView>(R.id.tvCoachName)
        var tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}