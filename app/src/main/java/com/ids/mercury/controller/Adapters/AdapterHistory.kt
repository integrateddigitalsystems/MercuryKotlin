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


class AdapterHistory(
    val items: ArrayList<History>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterHistory.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return VHItem(v)
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        var item=items[position]
        holder.tvTitle.text=item.description
        holder.tvDate.text=item.date


    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        var tvDate = itemView.findViewById<TextView>(R.id.tvDate)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}