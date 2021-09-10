package com.ids.mercury.controller.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener

import com.ids.mercury.model.response.History

import java.util.ArrayList


class AdapterLoyalityPoints(
    val items: ArrayList<History>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterLoyalityPoints.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_loyality, parent, false)
        return VHItem(v)
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        var item=items[position]
        holder.tvTitle.text=item.description
        holder.tvDate.text=item.date
        holder.tvPoints.text=item.loyaltyPoints.toString()

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        var tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        var tvPoints = itemView.findViewById<TextView>(R.id.tvPoints)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}