package com.android.asatabai


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.android.asatabai.data.JeepneyRoutesData

class JeepneyCodesActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeepney_codes)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, JeepneyRoutesData.jeepneyRoutes.map { "${it.code} - ${it.name}" })

        val listView = findViewById<ListView>(R.id.listViewJeepneyCodes)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedRoute = JeepneyRoutesData.jeepneyRoutes[position]

            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra("JEEPNEY_CODE", selectedRoute.code)
                putExtra("ROUTE_STOPS", ArrayList(selectedRoute.routeStops)) // Pass route coordinates
            }
            startActivity(intent)
        }
    }

}