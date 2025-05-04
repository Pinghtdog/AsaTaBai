package com.android.asatabai.data.JeepneyRoutes


import com.android.asatabai.data.JeepneyRoutes.JeepneyRoute
import com.google.android.gms.maps.model.LatLng

data class RouteResult(
    val jeepneyRoute: JeepneyRoute,
    val startStop: LatLng, // The specific stop to board
    val endStop: LatLng,   // The specific stop to alight
    val startStopIndex: Int, // Index of startStop in jeepneyRoute.locationStops
    val endStopIndex: Int,   // Index of endStop in jeepneyRoute.locationStops
    val walkToStartDistance: Float, // Meters from origin to startStop
    val walkFromEndDistance: Float  // Meters from endStop to destination
) {
    // You might add helper properties here later, e.g., descriptions
    val startStopDescription: String?
        get() = jeepneyRoute.locationStopsDescription.getOrNull(startStopIndex)

    val endStopDescription: String?
        get() = jeepneyRoute.locationStopsDescription.getOrNull(endStopIndex)
}