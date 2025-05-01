package com.android.asatabai.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asatabai.MapsActivity
import com.android.asatabai.R
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoute
import com.android.asatabai.data.JeepneyRoutes.RouteAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RecentsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RouteAdapter
    private lateinit var routeList: MutableList<JeepneyRoute>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recents, container, false)

        recyclerView = view.findViewById(R.id.recyclerRecentRoutes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        routeList = mutableListOf()
        adapter = RouteAdapter(routeList){ selectedRoute->
            val intent = Intent(context, MapsActivity::class.java).apply {
                putExtra("JEEPNEY_CODE", selectedRoute.code)
                putExtra("ROUTE_STOPS", ArrayList(selectedRoute.routeStops))
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        fetchRecentRoutesFromFirestore()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchRecentRoutesFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("recentRoutes")  // Use recentRoutes collection instead of jeepneyRoutes
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(6)
            .get()
            .addOnSuccessListener { result ->
                routeList.clear()
                for (document in result) {
                    val code = document.getString("code") ?: ""
                    val name = document.getString("name") ?: ""

                    val routeStops = (document["routeStops"] as? List<Map<String, Double>>)?.map {
                        LatLng(it["latitude"] ?: 0.0, it["longitude"] ?: 0.0)
                    } ?: emptyList()

                    val locationStops = (document["locationStops"] as? List<Map<String, Double>>)?.map {
                        LatLng(it["latitude"] ?: 0.0, it["longitude"] ?: 0.0)
                    } ?: emptyList()

                    val descriptions = document["locationStopsDescription"] as? List<String> ?: emptyList()

                    val route = JeepneyRoute(code, name, routeStops, locationStops, descriptions)
                    routeList.add(route)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching recent routes", e)
            }
    }

}
