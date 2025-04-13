package com.android.asatabai

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var routeStops: List<LatLng>
    private val cebuAreaBounds = LatLngBounds(
        LatLng(10.20, 123.65),  // Southwest corner (includes Toledo)
        LatLng(10.55, 124.00)   // Northeast corner (includes Lapu-Lapu, Cordova, Danao)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Get the selected jeepney code and route stops
        val jeepneyCode = intent.getStringExtra("JEEPNEY_CODE") ?: "01A"
        routeStops = intent.getParcelableArrayListExtra<LatLng>("ROUTE_STOPS") ?: emptyList()

        // Set the title
        supportActionBar?.title = "Jeepney $jeepneyCode Route"

        // Initialize map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Enable zoom controls
        map.uiSettings.isZoomControlsEnabled = true
        map.setLatLngBoundsForCameraTarget(cebuAreaBounds)
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(cebuAreaBounds, 100))

        if (routeStops.isNotEmpty()) {
            // Draw the route
            map.addPolyline(
                PolylineOptions()
                    .addAll(routeStops)
                    .color(Color.BLUE)
                    .width(8f)
            )
            // Add markers for stops
//            routeStops.forEachIndexed { index, latLng ->
//                map.addMarker(
//                    MarkerOptions()
//                        .position(latLng)
//                        .title("Stop ${index + 1}")
//                )
//            }

            // Zoom to show the entire route
            val bounds = LatLngBounds.builder()
            routeStops.forEach { bounds.include(it) }
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50))
        }else{
            Log.e("HI!!!", "Naa nasay error")
        }
    }
}