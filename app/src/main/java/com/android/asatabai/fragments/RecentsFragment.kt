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
    private lateinit var progressBar: View  // The ProgressBar view

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recents, container, false)

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerRecentRoutes)
        progressBar = view.findViewById(R.id.progressBar)  // Link to ProgressBar
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        routeList = mutableListOf()
        adapter = RouteAdapter(routeList) { selectedRoute ->
            // Handle route selection
            val db = FirebaseFirestore.getInstance()
            val recentRoutesRef = db.collection("recentRoutes")

            val recentRouteData = hashMapOf(
                "code" to selectedRoute.code,
                "name" to selectedRoute.name,
                "routeStops" to selectedRoute.routeStops.map { mapOf("latitude" to it.latitude, "longitude" to it.longitude) },
                "locationStops" to selectedRoute.locationStops.map { mapOf("latitude" to it.latitude, "longitude" to it.longitude) },
                "locationStopsDescription" to selectedRoute.locationStopsDescription,
                "timestamp" to System.currentTimeMillis()
            )

            // Check if a document with the same code and name exists
            recentRoutesRef
                .whereEqualTo("code", selectedRoute.code)
                .whereEqualTo("name", selectedRoute.name)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        // If exists, update timestamp
                        for (document in querySnapshot.documents) {
                            document.reference.update("timestamp", System.currentTimeMillis())
                        }
                    } else {
                        // If not exists, add new
                        recentRoutesRef.add(recentRouteData)
                    }

                    // After add/update, trim to keep only the 6 most recent
                    recentRoutesRef
                        .orderBy("timestamp")
                        .get()
                        .addOnSuccessListener { snapshot ->
                            val excess = snapshot.size() - 6
                            if (excess > 0) {
                                val docsToDelete = snapshot.documents.take(excess)
                                for (doc in docsToDelete) {
                                    doc.reference.delete()
                                }
                            }
                            fetchRecentRoutesFromFirestore()
                        }
                }

            val intent = Intent(context, MapsActivity::class.java).apply {
                putExtra("JEEPNEY_CODE", selectedRoute.code)
                putExtra("ROUTE_STOPS", ArrayList(selectedRoute.routeStops))
                putExtra("LOCATION_STOPS", ArrayList(selectedRoute.locationStops))
                putExtra("DESCRIPTIONS", ArrayList(selectedRoute.locationStopsDescription))
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter


        fetchRecentRoutesFromFirestore()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchRecentRoutesFromFirestore() {
        // Show progress bar before fetching data
        progressBar.visibility = View.VISIBLE

        val db = FirebaseFirestore.getInstance()
        db.collection("recentRoutes")
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

                // Hide progress bar after data is fetched
                progressBar.visibility = View.GONE
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching recent routes", e)
                // Hide progress bar in case of error
                progressBar.visibility = View.GONE
            }
    }
}
