package com.android.asatabai.data.Landmarks

import com.android.asatabai.R
import com.google.android.gms.maps.model.LatLng

object LandmarkData {

    // The list of landmarks (Places of Interest)
    // Define it directly here for simplicity if the list is relatively static.
    val placesOfInterest: List<Landmark> = listOf(
        Landmark(
            id = "magellans_cross",
            name = "Magellan's Cross",
            latLng = LatLng(10.2936, 123.9020),
            shortDescription = "Historic Christian cross",
            detailedDescription = "Planted by Portuguese and Spanish explorers led by Ferdinand Magellan upon arriving in Cebu in 1521.",
            type = "landmark",
            photo = R.drawable.magellanscross
        ),
        Landmark(
            id = "ayala_center",
            name = "Ayala Center Cebu",
            latLng = LatLng(10.3174, 123.9055),
            shortDescription = "Large shopping mall",
            detailedDescription = "Ayala Center Cebu is a major shopping mall located in the Cebu Business Park, featuring shops, restaurants, and cinemas.",
            type = "mall",
            photo = R.drawable.ayala
        ),
        Landmark(
            id = "cebu_city_hall",
            name = "Cebu City Hall",
            latLng = LatLng(10.2928, 123.9021),
            shortDescription = "Seat of city government",
            detailedDescription = "The executive building houses the office of the Cebu City Mayor and other city departments.",
            type = "government",
            photo = R.drawable.cebucityhall
        ),
        Landmark(
            id = "colon_st",
            name = "Colon Street",
            latLng = LatLng(10.2963, 123.8993), // Approximate center
            shortDescription = "Oldest street in the Philippines",
            detailedDescription = "Colon Street is a historical street in downtown Cebu City, considered the oldest national road in the Philippines.",
            type = "historic",
            photo = R.drawable.colonstreet
        ),
        Landmark(
            id = "sm_city_cebu",
            name = "SM City Cebu",
            latLng = LatLng(10.3117, 123.9184),
            shortDescription = "Major shopping mall",
            detailedDescription = "One of the largest shopping malls in Cebu, located near the port area.",
            type = "mall",
            photo = R.drawable.smcity
        )
        // Add all your other landmarks here...
    )


    fun findPlaceById(id: String): Landmark? {
        return placesOfInterest.find { it.id == id }
    }
}