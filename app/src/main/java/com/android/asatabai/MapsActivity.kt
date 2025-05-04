package com.android.asatabai

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.asatabai.data.Landmarks.Landmark
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.android.asatabai.fragments.HomeFragment

class MapsActivity : BaseActivity(), OnMapReadyCallback ,GoogleMap.OnMarkerClickListener, SensorEventListener {

    private lateinit var map: GoogleMap
    private lateinit var routeStops: List<LatLng>
    private lateinit var locationStops: List<LatLng>
    private lateinit var descriptions: List<String>
    private lateinit var infoPanel: LinearLayout
    private lateinit var infoTitle: TextView
    private lateinit var infoDescription: TextView
    private lateinit var compassView: ImageView
    private lateinit var backbtn : ImageView
    private lateinit var centerButton: ImageView
    private var lastClickedMarkerId: String? = null

//    private lateinit var sensorManager: SensorManager
//    private var accelerometer: Sensor? = null
//    private var magnetometer: Sensor? = null
    private var lastAccelerometer = FloatArray(3)
    private var lastMagnetometer = FloatArray(3)
    private var lastAccelerometerSet = false
    private var lastMagnetometerSet = false
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    private var currentDegree = 0.0f

    private val cebuCityHall = LatLng(10.2928, 123.9021)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        centerButton = findViewById(R.id.centerButtonactivity)
        infoPanel = findViewById(R.id.infoPanel)
        infoTitle = findViewById(R.id.infoTitle)
        infoDescription = findViewById(R.id.infoDescription)
        compassView = findViewById(R.id.compassViewactivity)
        backbtn = findViewById(R.id.backbtnmapsActivity)

        // Get the selected jeepney code and route stops
        val jeepneyCode = intent.getStringExtra("JEEPNEY_CODE") ?: "01A"
        routeStops = intent.getParcelableArrayListExtra<LatLng>("ROUTE_STOPS") ?: emptyList()
        locationStops = intent.getParcelableArrayListExtra<LatLng>("LOCATION_STOPS") ?: emptyList()
        descriptions = intent.getStringArrayListExtra("DESCRIPTIONS") ?: arrayListOf()


        // Title
        supportActionBar?.title = "Jeepney $jeepneyCode Route"

        // Map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        centerButton.setOnClickListener {
            // Check if map is initialized before using it
            if (::map.isInitialized) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(cebuCityHall, 15f))
                hideInfoPanel() // Hide info panel when centering
            } else {
                Toast.makeText(this, "Map not ready yet", Toast.LENGTH_SHORT).show()
            }
        }

        backbtn.setOnClickListener{
            finish();
        }



        // Sensors
//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
//        if (accelerometer == null || magnetometer == null) {
//            Toast.makeText(this, "Required sensors (Accelerometer or Magnetometer) not available.", Toast.LENGTH_LONG).show()
//            // Consider disabling compass functionality or hiding the view
//            compassView.visibility = View.GONE
//        }

        compassView.setOnClickListener {
            if (::map.isInitialized) {
                Log.d("MapsActivity", "Custom compass clicked. Resetting bearing and tilt.")
                val currentTarget = map.cameraPosition.target
                val currentZoom = map.cameraPosition.zoom
                // Animate camera to reset bearing and tilt
                map.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition.Builder()
                            .target(currentTarget) // Keep current location
                            .zoom(currentZoom)     // Keep current zoom
                            .bearing(0f)           // Reset bearing to North
                            .tilt(0f)              // Reset tilt
                            .build()
                    ),
                    500, // Animation duration in ms
                    null // Optional cancelable callback
                )
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // --- Basic Map Settings ---
        map.uiSettings.isZoomControlsEnabled = true // Show +/- zoom buttons
        map.uiSettings.isCompassEnabled = false

        if (routeStops.isNotEmpty()) {
            // Draw the route
            map.addPolyline(
                PolylineOptions()
                    .addAll(routeStops)
                    .color(Color.BLUE)
                    .width(8f)
                    .geodesic(true)
            )
            if (locationStops.isNotEmpty()) {
                for ((index, stop) in locationStops.withIndex()) {
                    val marker = map.addMarker(
                        MarkerOptions()
                            .position(stop)
                            .title("Location: ${(index + 'A'.code).toChar()}")
                    )
                    // Store the index in the marker's tag
                    marker?.tag = index
                }
            }
            // Zoom to show the entire route
            val bounds = LatLngBounds.builder()
            routeStops.forEach { bounds.include(it) }
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50))
        }else{
            Log.e("HI!!!", "Naa nasay error")
        }


        map.setOnMarkerClickListener(this)
        map.setOnMapClickListener {hideInfoPanel()}
        map.setOnInfoWindowClickListener { marker ->
            Toast.makeText(this, "Info window clicked for: ${marker.title}", Toast.LENGTH_SHORT).show()
            // You could navigate to a details screen here
        }


        // --- Move camera to initial position ---
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(cebuCityHall, 14f)) // Zoom level 14
        map.setOnCameraMoveListener {
            // Update compass rotation when map moves/rotates
            updateCompassRotation(map.cameraPosition.bearing)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Log.d("MapsActivity", "Marker clicked: ${marker.title}")

        // Hide if clicking the same marker again
        if (marker.id == lastClickedMarkerId && infoPanel.visibility == View.VISIBLE) {
            hideInfoPanel()
            lastClickedMarkerId = null
            return true
        }

        // Get the index we stored in the marker's tag
        val index = marker.tag as? Int

        if (index != null && index < descriptions.size) {
            showInfoPanel(marker.title ?: "Location Point", descriptions[index])
        } else {
            showInfoPanel(marker.title ?: "Location Point", "No description available")
        }

        map.animateCamera(CameraUpdateFactory.newLatLng(marker.position), 300, null)
        lastClickedMarkerId = marker.id
        return true
    }


    // --- Helper functions for the custom info panel ---
    private fun showInfoPanel(title: String, description: String) {
        infoTitle.text = title
        infoDescription.text = description
        infoPanel.visibility = View.VISIBLE
    }

    private fun hideInfoPanel() {
        infoPanel.visibility = View.GONE
    }

    private fun addPlacesToMap(places: List<Landmark>) {
        places.forEach { place ->
            addSinglePlaceToMap(place)
        }
    }

    private fun addSinglePlaceToMap(place: Landmark) {
        val marker = map.addMarker(
            MarkerOptions()
                .position(place.latLng)
                .title(place.name)
                .snippet(place.shortDescription)
        )
        marker?.tag = place
    }

//
//    override fun onResume() {
//        super.onResume()
//        // Register sensor listeners
//        if (accelerometer != null) {
//            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME) // Use SENSOR_DELAY_GAME or UI for responsiveness
//        }
//        if (magnetometer != null) {
//            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME)
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        // Unregister sensor listeners to save battery
//        sensorManager.unregisterListener(this, accelerometer)
//        sensorManager.unregisterListener(this, magnetometer)
//    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.size)
            lastAccelerometerSet = true
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.size)
            lastMagnetometerSet = true
        }
        if (lastAccelerometerSet && lastMagnetometerSet) {
            val success = SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer)
            if (success) {
                SensorManager.getOrientation(rotationMatrix, orientationAngles)

                // Azimuth contains the angle around the Z axis (0=North, 90=East, 180=South, 270=West)
                // It's in radians, convert to degrees.
                val azimuthInRadians = orientationAngles[0]
                val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).toFloat()
                val mapBearing = if (::map.isInitialized) map.cameraPosition.bearing else 0f

                updateCompassRotation(mapBearing, azimuthInDegrees)
            }
        }
    }

    private fun updateCompassRotation(mapBearing: Float, deviceAzimuthDegrees: Float = -1f) {
        var azimuth = deviceAzimuthDegrees
        if (azimuth == -1f) {
            azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
        }
        val compassRotation = mapBearing - azimuth
        val rotateAnimation = RotateAnimation(
            currentDegree, // from degree
            -compassRotation, // to degree (-ve because image North is up, azimuth increases Eastward)
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point X (center)
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot point Y (center)
        )
        rotateAnimation.duration = 250 // Milliseconds for the animation
        rotateAnimation.fillAfter = true // Keep the state after animation ends
        compassView.startAnimation(rotateAnimation)
        currentDegree = -compassRotation
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("MapsActivity", "Sensor accuracy changed: ${sensor?.name} to $accuracy")
    }

}