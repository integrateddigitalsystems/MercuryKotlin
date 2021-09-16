package com.ids.mercury.controller.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnSubItemClickListener
import com.ids.mercury.model.response.ClassSession
import java.util.ArrayList

class AdapterScheduleSection(
    val items: ArrayList<ClassSession>,
    private val itemClickListener: RVOnSubItemClickListener,
    private val parentPosition:Int
) :
    RecyclerView.Adapter<AdapterScheduleSection.VHItem>() {

    var itemParentPosition=parentPosition
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        return VHItem(
            LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_inside, parent, false)
        )
    }


    override fun onBindViewHolder(holder: VHItem, position: Int) {
        try{holder.tvTitle.text = items[position].className}catch (e:Exception){}
        try{holder.tvCoachName.text = items[position].coach}catch (e:Exception){}

        try{
            @SuppressLint("SetTextI18n")
            holder.tvDate.text =  items[position].time + " - "+items[position].endTime

        }catch (e:Exception){}
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        var tvCoachName = itemView.findViewById<TextView>(R.id.tvCoachName)
        var tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onSubItemClicked(v, layoutPosition,parentPosition)
        }
    }
}