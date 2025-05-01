package com.android.asatabai

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoutesData
import com.android.asatabai.data.JeepneyRoutes.RouteAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class JeepneyCodesActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeepney_codes)

        val recyclerView = findViewById<RecyclerView>(R.id.listViewJeepneyCodes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = RouteAdapter(JeepneyRoutesData.jeepneyRoutes) { selectedRoute ->
            val db = FirebaseFirestore.getInstance()
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@RouteAdapter
            val recentRoutesRef = db.collection("users").document(userId).collection("recentRoutes")

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
                        }
                }

            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra("JEEPNEY_CODE", selectedRoute.code)
                putExtra("ROUTE_STOPS", ArrayList(selectedRoute.routeStops))
                putExtra("LOCATION_STOPS", ArrayList(selectedRoute.locationStops))
                putExtra("DESCRIPTIONS", ArrayList(selectedRoute.locationStopsDescription))
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter


    }

}