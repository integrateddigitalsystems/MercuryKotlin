package com.ids.mercury.controller.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.Notifications
import com.ids.mercury.model.SectionPagerItem
import com.ids.mercury.model.response.ItemNotification
import com.ids.mercury.utils.AppHelper
import com.ids.mercury.utils.hide
import com.ids.mercury.utils.show
import java.util.ArrayList


class AdapterNotifcations(
    val items: ArrayList<ItemNotification>,
    private val itemClickListener: RVOnItemClickListener
) :
    RecyclerView.Adapter<AdapterNotifcations.VHItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return VHItem(v)
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        var item=items[position]

        //show this code if api updated with image
 /*       if(item.expanded){
            holder.linearExpanded.show()
            holder.ivArrow.rotation=180f
        }
        else{
            holder.linearExpanded.hide()
            holder.ivArrow.rotation=0f
        }*/
        holder.ivArrow.hide()

        try{holder.tvTitle.text=item.notificationText}catch (e:Exception){}
        try{holder.tvDate.text= AppHelper.formatDateTimestamp(AppHelper.dateFormat1, item.sentDate!!,true)}catch (e:Exception){}

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        var tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        var ivNotification = itemView.findViewById<ImageView>(R.id.ivNotification)
        var linearExpanded = itemView.findViewById<LinearLayout>(R.id.linearExpanded)
        var ivArrow = itemView.findViewById<ImageView>(R.id.ivArrow)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}