package com.android.asatabai.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.android.asatabai.R
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoutesData

class JeepneyCodesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        return inflater.inflate(R.layout.fragment_jeepney_codes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.listViewJeepneyCodes)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            JeepneyRoutesData.jeepneyRoutes.map {
                "\n${it.code} - ${it.name}\n\nDirection:\n${it.direction}\n"
            }
        )

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedRoute = JeepneyRoutesData.jeepneyRoutes[position]

            val bundle = Bundle().apply {
                putString("JEEPNEY_CODE", selectedRoute.code)
                putParcelableArrayList("ROUTE_STOPS", ArrayList(selectedRoute.routeStops))
            }

            val fragment = JeepneyRoutesMapFragment().apply {
                arguments = bundle
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.frame, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
