package com.ids.mercury.controller.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener

import com.ids.mercury.model.response.History
import com.ids.mercury.model.response.MemberShip
import java.lang.Exception

import java.util.ArrayList


class AdapterMembership(
    val items: ArrayList<MemberShip>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterMembership.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_membership, parent, false)
        return VHItem(v)
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        var item=items[position]
        try{holder.tvPackageType.text=item.packageName}catch (e:Exception){}
        try{holder.tvStartDate.text=item.registrationDate}catch (e:Exception){}
        try{holder.tvEndDate.text=item.expiryDate}catch (e:Exception){}
        try{holder.tvStatus.text=item.entriesNbLeft.toString()}catch (e:Exception){}

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvPackageType = itemView.findViewById<TextView>(R.id.tvPackageType)
        var tvStartDate = itemView.findViewById<TextView>(R.id.tvStartDate)
        var tvEndDate = itemView.findViewById<TextView>(R.id.tvEndDate)
        var tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}