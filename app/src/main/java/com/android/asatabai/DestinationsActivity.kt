package com.android.asatabai

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asatabai.data.Landmarks.LandmarkAdapter
import com.android.asatabai.data.Landmarks.LandmarkData

class DestinationsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations)
        val recyclerView = findViewById<RecyclerView>(R.id.listviewlandmarks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = LandmarkAdapter(LandmarkData.placesOfInterest){ selectedLandmark ->

            val intent = Intent(this, LandmarkMapActivity::class.java).apply {
                putExtra("NAME", selectedLandmark.name)
                putExtra("TYPE", selectedLandmark.type)
                putExtra("DETAILED DESCRIPTION", selectedLandmark.detailedDescription)
                putExtra("LATLNG", selectedLandmark.latLng)
            }

            startActivity(intent)
        }
        recyclerView.adapter = adapter

    }
}