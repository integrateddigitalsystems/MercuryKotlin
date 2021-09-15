package com.ids.mercury.controller.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.GymPackage
import com.ids.mercury.model.response.PtPackage
import com.ids.mercury.utils.AppHelper


import java.util.ArrayList

class AdapterPTPackages(
    val items: ArrayList<PtPackage>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterPTPackages.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        return VHItem(
            LayoutInflater.from(parent.context).inflate(R.layout.item_popup, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        holder.tvName.text = items[position].name

        if(MyApplication.selectedPtPackageId==items[position].id)
            holder.tvName.typeface= AppHelper.getTypeFaceBold(holder.itemView.context)
        else
            holder.tvName.typeface= AppHelper.getTypeFace(holder.itemView.context)
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