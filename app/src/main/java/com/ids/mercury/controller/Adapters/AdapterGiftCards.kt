package com.ids.mercury.controller.Adapters

import com.ids.mercury.model.GiftCards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener

import com.ids.mercury.model.response.History
import com.ids.mercury.model.response.InvoicePayments

import java.util.ArrayList


class AdapterGiftCards(
    val items: ArrayList<GiftCards>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterGiftCards.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_gift_card, parent, false)
        return VHItem(v)
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        var item=items[position]
        try{holder.tvAmount.text=item.name}catch (e:Exception){}
        if(item.selected!!)
            holder.itemView.setBackgroundResource(R.drawable.white_border_layout)
        else
            holder.itemView.setBackgroundResource(R.drawable.gray_border_layout)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvAmount = itemView.findViewById<TextView>(R.id.tvAmount)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}