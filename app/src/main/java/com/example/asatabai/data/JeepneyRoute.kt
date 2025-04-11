package com.example.asatabai.data

import com.google.android.gms.maps.model.LatLng

data class JeepneyRoute(
    val code: String,
    val name: String,
    val routeStops: List<LatLng>
)