package com.android.asatabai

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class DeveloperAdapter(
    private val context: Context,
    private val developers: List<Developer>
)   : ArrayAdapter<Developer>(context, 0, developers) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_developer, parent, false)
        }
        val developer = getItem(position)
        view?.findViewById<ImageView>(R.id.developerImage)?.setImageResource(developer?.imageRes ?: 0)
        view?.findViewById<TextView>(R.id.developerDescription)?.text =
            "${developer?.name}\n\n${developer?.description}"
        return view!!
    }
}