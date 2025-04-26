package com.android.asatabai

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asatabai.data.AppData
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoutesData
import com.android.asatabai.data.JeepneyRoutes.RouteAdapter

class JeepneyCodesActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeepney_codes)

        val recyclerView = findViewById<RecyclerView>(R.id.listViewJeepneyCodes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = RouteAdapter(JeepneyRoutesData.jeepneyRoutes){ selectedRoute ->
            val appData = application as AppData

            if (!appData.recentRoutes.contains(selectedRoute)) {
                if (appData.recentRoutes.size >= 6) {
                    appData.recentRoutes.removeAt(0)
                }
                appData.recentRoutes.add(selectedRoute)
            }

            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra("JEEPNEY_CODE", selectedRoute.code)
                putExtra("ROUTE_STOPS", ArrayList(selectedRoute.routeStops))
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter


    }

}