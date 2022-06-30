package com.mahyco.assetsverification.side_menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mahyco.assetsverification.R
import com.mahyco.assetsverification.side_menu.model.SideMenuModel

class NavigationDrawerAdapter(
    val context: Context,
    val menuList: ArrayList<SideMenuModel>,
    private val onMenuItemClick: onMenuItemClick
) : RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val menu_icon: TextView = view.findViewById(R.id.menu_icon)
        val menu_name: TextView = view.findViewById(R.id.menu_name)
        val itemView: LinearLayout = view.findViewById(R.id.itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.menu_name.text = menuList.get(position).Name
        holder.menu_icon.setBackgroundResource(menuList.get(position).icon)
        if (menuList.get(position).isSelected) {
            holder.menu_name.setTextColor(context.getColor(R.color.white))
            holder.menu_icon.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.white)
            holder.itemView.background = context.getDrawable(R.drawable.item_selected_bg)
        } else {
            holder.menu_name.setTextColor(context.getColor(R.color.black))
            holder.menu_icon.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.black)
            holder.itemView.background = null
        }
        holder.itemView.setOnClickListener {
            onMenuItemClick.onItemSelect(position)
            Toast.makeText(context, "toast", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return menuList.count()
    }


}
public interface onMenuItemClick {
    fun onItemSelect(position: Int)
}