package com.ids.mercury.controller.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.model.response.Court



import java.util.ArrayList

class AdapterCourts(
    val items: ArrayList<Court>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterCourts.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        return VHItem(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rent_court, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        holder.tvCourt.text = items[position].name
        if(items[position].isHalf!!)
           holder.tvInfo.text=holder.itemView.context.getString(R.string.half)
        else
            holder.tvInfo.text=holder.itemView.context.getString(R.string.full)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvCourt = itemView.findViewById<TextView>(R.id.tvCourt)
        var tvInfo = itemView.findViewById<TextView>(R.id.tvInfo)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}