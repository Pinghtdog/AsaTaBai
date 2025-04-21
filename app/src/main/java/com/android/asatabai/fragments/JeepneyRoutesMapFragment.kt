package com.android.asatabai.fragments

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.asatabai.R
import com.android.asatabai.data.Landmarks.Landmark
import com.android.asatabai.data.Landmarks.LandmarkData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class JeepneyRoutesMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, SensorEventListener {

    private lateinit var map: GoogleMap
    private lateinit var routeStops: List<LatLng>
    private lateinit var infoPanel: LinearLayout
    private lateinit var infoTitle: TextView
    private lateinit var infoDescription: TextView
    private lateinit var compassView: ImageView
    private lateinit var centerButton: ImageView

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null
    private var lastAccelerometer = FloatArray(3)
    private var lastMagnetometer = FloatArray(3)
    private var lastAccelerometerSet = false
    private var lastMagnetometerSet = false
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    private var currentDegree = 0.0f

    private val cebuCityHall = LatLng(10.2928, 123.9021)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jeepney_routes_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        centerButton = view.findViewById(R.id.centerButton)
        infoPanel = view.findViewById(R.id.infoPanel)
        infoTitle = view.findViewById(R.id.infoTitle)
        infoDescription = view.findViewById(R.id.infoDescription)
        compassView = view.findViewById(R.id.compassView)

        // Get the selected jeepney code and route stops from arguments
        arguments?.let {
            val jeepneyCode = it.getString("JEEPNEY_CODE") ?: "01A"
            routeStops = it.getParcelableArrayList("ROUTE_STOPS") ?: emptyList()

            // Update title in hosting activity if needed
            activity?.title = "Jeepney $jeepneyCode Route"
        }

        // Initialize map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            ?: SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction().replace(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)

        centerButton.setOnClickListener {
            if (::map.isInitialized) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(cebuCityHall, 15f))
                hideInfoPanel()
            }
        }

        // Sensors
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        if (accelerometer == null || magnetometer == null) {
            compassView.visibility = View.GONE
        }

        compassView.setOnClickListener {
            if (::map.isInitialized) {
                val currentTarget = map.cameraPosition.target
                val currentZoom = map.cameraPosition.zoom
                map.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition.Builder()
                            .target(currentTarget)
                            .zoom(currentZoom)
                            .bearing(0f)
                            .tilt(0f)
                            .build()
                    ),
                    500,
                    null
                )
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isCompassEnabled = false

        if (routeStops.isNotEmpty()) {
            map.addPolyline(
                PolylineOptions()
                    .addAll(routeStops)
                    .color(Color.BLUE)
                    .width(8f)
                    .geodesic(true)
            )
            val landmarks = LandmarkData.placesOfInterest
            addPlacesToMap(landmarks)

            val bounds = LatLngBounds.builder()
            routeStops.forEach { bounds.include(it) }
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50))
        } else {
            Log.e("MapsFragment", "No route stops provided")
        }

        map.setOnMarkerClickListener(this)
        map.setOnMapClickListener { hideInfoPanel() }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cebuCityHall, 14f))
        map.setOnCameraMoveListener {
            updateCompassRotation(map.cameraPosition.bearing)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val landmark = marker.tag as? Landmark
        if (landmark != null) {
            showInfoPanel(landmark.name, landmark.detailedDescription)
            map.animateCamera(CameraUpdateFactory.newLatLng(marker.position), 300, null)
            return true
        } else {
            showInfoPanel(marker.title ?: "Unknown", marker.snippet ?: "No details")
            map.animateCamera(CameraUpdateFactory.newLatLng(marker.position), 300, null)
            return true
        }
    }

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
            val marker = map.addMarker(
                MarkerOptions()
                    .position(place.latLng)
                    .title(place.name)
                    .snippet(place.shortDescription)
            )
            marker?.tag = place
        }
    }

    override fun onResume() {
        super.onResume()
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        }
        if (magnetometer != null) {
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.size)
                lastAccelerometerSet = true
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.size)
                lastMagnetometerSet = true
            }
        }

        if (lastAccelerometerSet && lastMagnetometerSet) {
            val success = SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer)
            if (success) {
                SensorManager.getOrientation(rotationMatrix, orientationAngles)
                val azimuthInDegrees = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
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
            currentDegree,
            -compassRotation,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = 250
        rotateAnimation.fillAfter = true
        compassView.startAnimation(rotateAnimation)
        currentDegree = -compassRotation
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("MapsFragment", "Sensor accuracy changed: ${sensor?.name} to $accuracy")
    }

    companion object {
        fun newInstance(jeepneyCode: String, routeStops: ArrayList<LatLng>): JeepneyRoutesMapFragment {
            return JeepneyRoutesMapFragment().apply {
                arguments = Bundle().apply {
                    putString("JEEPNEY_CODE", jeepneyCode)
                    putParcelableArrayList("ROUTE_STOPS", routeStops)
                }
            }
        }
    }

}