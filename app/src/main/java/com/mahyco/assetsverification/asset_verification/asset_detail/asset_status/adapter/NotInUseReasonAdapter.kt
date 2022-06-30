package com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahyco.assetsverification.R

class NotInUseReasonAdapter(val menuList: ArrayList<NotInUseReasonModel>, val onCLick: onCLick) :
    RecyclerView.Adapter<NotInUseReasonAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val itemViewLayout: LinearLayout = itemView.findViewById(R.id.itemViewLayout)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_reason, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewName.text = menuList.get(position).name
        holder.itemViewLayout.setOnClickListener {
            onCLick.onReasonSelect(position)

        }
    }
    override fun getItemCount(): Int {
        return menuList.size
    }
}

interface onCLick {
    fun onReasonSelect(position: Int)
}