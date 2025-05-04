package com.android.asatabai.data.JeepneyRoutes

import android.location.Location
import android.util.Log
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoute
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoutesData
import com.google.android.gms.maps.model.LatLng
import kotlin.math.min

class JeepneyRouter {

    companion object {
        // Helper function to calculate distance between two LatLng points in meters
        fun distanceBetween(point1: LatLng, point2: LatLng): Float {
            val results = FloatArray(1)
            try {
                Location.distanceBetween(
                    point1.latitude, point1.longitude,
                    point2.latitude, point2.longitude,
                    results
                )
            } catch (e: IllegalArgumentException) {
                Log.e("JeepneyRouter", "Invalid LatLng for distance calculation: $point1, $point2", e)
                return Float.MAX_VALUE // Return a large value on error
            }
            return results[0]
        }
    }

    // --- Main Routing Function (Direct Routes Only) ---
    fun findDirectRoutes(
        origin: LatLng,
        destination: LatLng,
        maxWalkDistanceMeters: Double = 500.0 // Max distance user willing to walk to/from a stop
    ): List<RouteResult> {

        val potentialRoutes = mutableListOf<RouteResult>()
        val allJeepneyRoutes = JeepneyRoutesData.jeepneyRoutes // Get the initialized data

        if (allJeepneyRoutes.isEmpty()) {
            Log.w("JeepneyRouter", "JeepneyRoutesData is not initialized or empty.")
            return emptyList()
        }

        // Basic check if origin is very far from Cebu (replace with better check if needed)
        // Example: Using Cebu City Hall as a reference point
        val cebuCityHall = LatLng(10.2928, 123.9021)
        if (distanceBetween(origin, cebuCityHall) > 100000) { // e.g., > 100 km
            Log.w("JeepneyRouter", "Origin is likely outside Cebu service area.")
            // Decide how to handle: return empty list, show message, etc.
            // For now, we proceed, but routes likely won't be found if origin is truly far.
            // return emptyList() // Or uncomment this to stop if origin is far
        }


        // 1. Find all stops near the origin and destination
        val nearbyStartStops = mutableMapOf<JeepneyRoute, MutableList<Pair<Int, Float>>>() // Route -> List<(StopIndex, Distance)>
        val nearbyEndStops = mutableMapOf<JeepneyRoute, MutableList<Pair<Int, Float>>>()   // Route -> List<(StopIndex, Distance)>

        allJeepneyRoutes.forEach { route ->
            route.locationStops.forEachIndexed { index, stop ->
                val distToOrigin = distanceBetween(origin, stop)
                if (distToOrigin <= maxWalkDistanceMeters) {
                    nearbyStartStops.computeIfAbsent(route) { mutableListOf() }.add(Pair(index, distToOrigin))
                }

                val distToDestination = distanceBetween(destination, stop)
                if (distToDestination <= maxWalkDistanceMeters) {
                    nearbyEndStops.computeIfAbsent(route) { mutableListOf() }.add(Pair(index, distToDestination))
                }
            }
        }

        // 2. Iterate through routes that have BOTH nearby start and end stops
        val commonRoutes = nearbyStartStops.keys.intersect(nearbyEndStops.keys)

        commonRoutes.forEach { route ->
            val possibleStarts = nearbyStartStops[route] ?: emptyList()
            val possibleEnds = nearbyEndStops[route] ?: emptyList()

            // 3. Check for valid start/end pairs within the *same* route
            possibleStarts.forEach { (startIndex, walkToStartDist) ->
                possibleEnds.forEach { (endIndex, walkFromEndDist) ->
                    // *** Crucial Check: Ensure the end stop comes AFTER the start stop in the route sequence ***
                    if (endIndex > startIndex) {
                        // Found a potential direct route
                        val startStopLatLng = route.locationStops[startIndex]
                        val endStopLatLng = route.locationStops[endIndex]

                        potentialRoutes.add(
                            RouteResult(
                                jeepneyRoute = route,
                                startStop = startStopLatLng,
                                endStop = endStopLatLng,
                                startStopIndex = startIndex,
                                endStopIndex = endIndex,
                                walkToStartDistance = walkToStartDist,
                                walkFromEndDistance = walkFromEndDist
                            )
                        )
                    }
                }
            }
        }

        // 4. Optional: Sort or filter the results (e.g., by shortest total walk distance)
        potentialRoutes.sortBy { it.walkToStartDistance + it.walkFromEndDistance }

        return potentialRoutes
    }
}