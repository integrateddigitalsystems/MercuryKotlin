package com.ids.mercury.controller.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.model.GiftCards
import com.ids.mercury.model.response.Coach
import java.util.ArrayList

class AdapterCoachesPT(
    val items: ArrayList<Coach>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterCoachesPT.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_coaches, parent, false)
        return VHItem(v)
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        var item=items[position]
        try{holder.tvAmount.text=item.name}catch (e:Exception){}
        if(item.selected!!)
            holder.itemView.setBackgroundResource(R.drawable.gray_white_border)
        else
            holder.itemView.setBackgroundResource(R.drawable.gray_back_no_border)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvAmount = itemView.findViewById<TextView>(R.id.text)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}