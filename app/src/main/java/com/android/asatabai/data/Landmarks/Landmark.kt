package com.android.asatabai.data.Landmarks

import com.android.asatabai.R
import com.google.android.gms.maps.model.LatLng

// Data class to hold all information about a point of interest
data class Landmark(
    val id: String,           // A unique identifier for this place
    val name: String,         // The main title for the marker/info panel
    val latLng: LatLng,       // The geographic coordinates
    val shortDescription: String, // Text for the default marker snippet (optional)
    val detailedDescription: String, // Text for your custom info panel
    val type: String? = null,// Optional: Category like "landmark", "mall", "stop", etc.
    val photo: Int = R.drawable.map
)