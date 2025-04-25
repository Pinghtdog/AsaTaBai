package com.android.asatabai.data.JeepneyRoutes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.asatabai.R

class RouteAdapter(private val routeList: List<JeepneyRoute>, private val onItemClick: (JeepneyRoute) -> Unit) :
    RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_recents, parent, false)
        return RouteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        val route = routeList[position]
        holder.codeText.text = route.code
        holder.nameText.text = route.name
        holder.bind(route)
    }

    override fun getItemCount(): Int = routeList.size

    inner class RouteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codeText: TextView = itemView.findViewById(R.id.textRouteCode)
        val nameText: TextView = itemView.findViewById(R.id.textRouteName)
        fun bind(route: JeepneyRoute){
            itemView.setOnClickListener {
                onItemClick(route)
            }
        }
    }
}
