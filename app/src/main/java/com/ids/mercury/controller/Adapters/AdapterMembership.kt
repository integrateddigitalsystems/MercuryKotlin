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
import com.ids.mercury.utils.AppConstants
import com.ids.mercury.utils.hide
import com.ids.mercury.utils.show
import java.lang.Exception

import java.util.ArrayList


class AdapterMembership(
    val items: ArrayList<MemberShip>,
    private val itemClickListener: RVOnItemClickListener,
    var type:Int
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
        try{holder.tvStatus.text= item.statusName}catch (e:Exception){}
            if(type == AppConstants.TYPE_GYM){
                holder.tvSessionsLeftTitle.hide()
                holder.tvSessionsLeftValue.hide()
            }else{
                holder.tvSessionsLeftTitle.show()
                holder.tvSessionsLeftValue.show()
                try{ holder.tvSessionsLeftValue.text=item.entriesNbLeft.toString() }catch (e:Exception){}
            }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvPackageType = itemView.findViewById<TextView>(R.id.tvPackageType)
        var tvStartDate = itemView.findViewById<TextView>(R.id.tvStartDate)
        var tvEndDate = itemView.findViewById<TextView>(R.id.tvEndDate)
        var tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)

        var tvSessionsLeftTitle = itemView.findViewById<TextView>(R.id.tvSessionsLeftTitle)
        var tvSessionsLeftValue = itemView.findViewById<TextView>(R.id.tvSessionsLeftValue)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}