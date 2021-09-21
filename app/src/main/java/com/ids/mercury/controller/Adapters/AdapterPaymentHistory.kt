package com.ids.mercury.controller.Adapters

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


class AdapterPaymentHistory(
    val items: ArrayList<InvoicePayments>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterPaymentHistory.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_payment_history, parent, false)
        return VHItem(v)
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        var item=items[position]
        try{holder.tvAmount.text=item.amount+" "+item.currencyName}catch (e:Exception){}
        try{holder.tvDate.text=item.paymentDate.toString()}catch (e:Exception){}

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvAmount = itemView.findViewById<TextView>(R.id.tvAmount)
        var tvDate = itemView.findViewById<TextView>(R.id.tvDate)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}