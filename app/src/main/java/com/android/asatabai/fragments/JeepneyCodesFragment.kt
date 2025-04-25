package com.android.asatabai.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asatabai.MapsActivity
import com.android.asatabai.R
import com.android.asatabai.data.AppData
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoutesData
import com.android.asatabai.data.JeepneyRoutes.RouteAdapter

class JeepneyCodesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RouteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_jeepney_codes, container, false)



        recyclerView = view.findViewById(R.id.listViewJeepneyCodes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = RouteAdapter(JeepneyRoutesData.jeepneyRoutes){ selectedRoute ->
            val appData = requireActivity().application as AppData

            if (!appData.recentRoutes.contains(selectedRoute)) {
                if (appData.recentRoutes.size >= 6) {
                    appData.recentRoutes.removeAt(0)
                }
                appData.recentRoutes.add(selectedRoute)
            }

            val intent = Intent(context, MapsActivity::class.java).apply {
                putExtra("JEEPNEY_CODE", selectedRoute.code)
                putExtra("ROUTE_STOPS", ArrayList(selectedRoute.routeStops))
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter



        return view
    }

}
