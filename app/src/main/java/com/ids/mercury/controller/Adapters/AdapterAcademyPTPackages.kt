package com.ids.mercury.controller.Adapters

import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Activities.ActivityInsideClasses
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.model.response.AcademyPtPackage
import com.ids.mercury.utils.PaymentApiDialog
import kotlinx.android.synthetic.main.item_academy_pt_package.view.*
import java.lang.Exception


import java.util.ArrayList

class AdapterAcademyPTPackages(val items: ArrayList<AcademyPtPackage>, private val itemClickListener: RVOnItemClickListener) :
    RecyclerView.Adapter<AdapterAcademyPTPackages.VHItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        return VHItem(
            LayoutInflater.from(parent.context).inflate(R.layout.item_academy_pt_package, parent, false)
        )
    }
    override fun onBindViewHolder(holder: VHItem, position: Int) {
        var item=items[position]
        var isChecked = item.isMultiUser!!
        try{holder.tvName.text=item.name}catch (e: Exception){}
        try{holder.tvAmount.text=item.amount.toString()}catch (e: Exception){}
        try{holder.tvAmount.text=item.amount.toString()}catch (e: Exception){}
        holder.ivShow.setImageResource(if(isChecked) R.drawable.icon_true else R.drawable.icon_false)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvName = itemView.findViewById<TextView>(R.id.tvName)
        var tvAmount = itemView.findViewById<TextView>(R.id.tvAmount)
        var ivShow = itemView.findViewById<ImageView>(R.id.ivShow)

        init {
            itemView.setOnClickListener(this)
             ivShow.setOnClickListener(this)
        }

        override fun onClick(v: View) {
           itemClickListener.onItemClicked(v, layoutPosition)
        }
    }

}