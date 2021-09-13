package com.ids.mercury.controller.Adapters



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.CountryCodes
import com.ids.mercury.model.response.GymPackage
import com.ids.mercury.utils.AppHelper


import java.util.ArrayList

class AdapterGymPackages(
    val items: ArrayList<GymPackage>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterGymPackages.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        return VHItem(
            LayoutInflater.from(parent.context).inflate(R.layout.item_country_code, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        holder.tvCodeName.text = items[position].name

/*        if(MyApplication.selectedItemDialog.replace("+","").trim()==items[position].code!!.replace("+","").trim())
            holder.tvCodeName.typeface= AppHelper.getTypeFaceBold(holder.itemView.context)
        else
            holder.tvCodeName.typeface= AppHelper.getTypeFace(holder.itemView.context)*/
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvCodeName = itemView.findViewById<TextView>(R.id.tvCodeName)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}