package com.android.asatabai.data.JeepneyRoutes

import com.google.android.gms.maps.model.LatLng

data class JeepneyRoute(
    val code: String,
    val name: String,
    val direction: String,
    val routeStops: List<LatLng>
)