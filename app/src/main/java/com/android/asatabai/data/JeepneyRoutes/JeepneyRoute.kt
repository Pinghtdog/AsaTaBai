package com.android.asatabai.data.JeepneyRoutes

import com.google.android.gms.maps.model.LatLng

data class JeepneyRoute(
    val code: String,
    val name: String,
    val routeStops: List<LatLng>,
    val locationStops: List<LatLng>,
    val locationStopsDescription: List<String>
)