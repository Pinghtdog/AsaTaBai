package com.android.asatabai.data.JeepneyRoutes

import com.google.android.gms.maps.model.LatLng

data class Destination(
    val name: String,
    val location: LatLng,
    val jeepneyCodes: List<String>
)
