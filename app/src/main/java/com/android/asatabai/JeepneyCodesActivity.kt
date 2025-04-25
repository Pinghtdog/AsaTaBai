package com.android.asatabai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.android.asatabai.data.AppData
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoutesData

class JeepneyCodesActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeepney_codes)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, JeepneyRoutesData.jeepneyRoutes.map { "${it.code} - ${it.name}" })

        val listView = findViewById<ListView>(R.id.listViewJeepneyCodes)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedRoute = JeepneyRoutesData.jeepneyRoutes[position]
            val appData = (application as AppData)
            if (!appData.recentRoutes.contains(selectedRoute)) {
                if (appData.recentRoutes.size >= 6) {
                    appData.recentRoutes.removeAt(appData.recentRoutes.size-1)
                }
                appData.recentRoutes.add(0,selectedRoute)
                Log.d("Debug", "RecentRoutes: ${appData.recentRoutes.map { it.code }}")

            }

            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra("JEEPNEY_CODE", selectedRoute.code)
                putExtra("ROUTE_STOPS", ArrayList(selectedRoute.routeStops)) // Pass route coordinates
            }
            startActivity(intent)
        }
    }

}