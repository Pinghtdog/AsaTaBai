package com.android.asatabai

import android.content.Context
import android.content.Intent
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class LandmarkMapActivity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, SensorEventListener {

    private lateinit var map: GoogleMap
    private lateinit var landmarkName: String
    private lateinit var landmarkType: String
    private lateinit var landmarkDescription: String
    private lateinit var latlng: LatLng
    private lateinit var infoPanel: LinearLayout
    private lateinit var infoTitle: TextView
    private lateinit var infoDescription: TextView
    private lateinit var compassView: ImageView
    private lateinit var centerButton: ImageView
    private lateinit var backButton: ImageView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        centerButton = findViewById(R.id.centerButtonactivity)
        infoPanel = findViewById(R.id.infoPanel)
        infoTitle = findViewById(R.id.infoTitle)
        infoDescription = findViewById(R.id.infoDescription)
        compassView = findViewById(R.id.compassViewactivity)
        backButton = findViewById(R.id.backbtnmapsActivity)

        // Get the landmark data from intent
        landmarkName = intent.getStringExtra("NAME") ?: "Unknown"
        landmarkType = intent.getStringExtra("TYPE") ?: "Unknown"
        landmarkDescription = intent.getStringExtra("DETAILED DESCRIPTION") ?: "Unknown"
        latlng = intent.getParcelableExtra("LATLNG") ?: LatLng(10.2928, 123.9021)

        supportActionBar?.title = landmarkName

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        backButton.setOnClickListener {
            startActivity(Intent(this, DestinationsActivity::class.java))
        }
        centerButton.setOnClickListener {
            if (::map.isInitialized) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15f))
                hideInfoPanel()
            } else {
                Toast.makeText(this, "Map not ready yet", Toast.LENGTH_SHORT).show()
            }
        }

        // Sensors setup
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        if (accelerometer == null || magnetometer == null) {
            Toast.makeText(this, "Required sensors not available", Toast.LENGTH_LONG).show()
            compassView.visibility = View.GONE
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Basic Map Settings
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isCompassEnabled = false

        // Add marker for the landmark
        val marker = map.addMarker(
            MarkerOptions()
                .position(latlng)
                .title(landmarkName)
                .snippet(landmarkType)
        )

        // Show info window immediately
        marker?.showInfoWindow()

        // Move camera to the landmark
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17f))

        // Set listeners
        map.setOnMarkerClickListener(this)
        map.setOnMapClickListener { hideInfoPanel() }
        map.setOnCameraMoveListener {
            updateCompassRotation(map.cameraPosition.bearing)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // Toggle info panel visibility when marker is clicked
        if (infoPanel.visibility == View.VISIBLE) {
            hideInfoPanel()
        } else {
            showInfoPanel()
        }
        return true
    }

    private fun showInfoPanel() {
        infoTitle.text = "Name: $landmarkName\nType: $landmarkType"
        infoDescription.text = landmarkDescription
        infoPanel.visibility = View.VISIBLE
    }

    private fun hideInfoPanel() {
        infoPanel.visibility = View.GONE
    }

    // Sensor-related methods remain the same
    override fun onResume() {
        super.onResume()
        accelerometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME) }
        magnetometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME) }
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
            if (SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer)) {
                SensorManager.getOrientation(rotationMatrix, orientationAngles)
                val azimuthInDegrees = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
                val mapBearing = if (::map.isInitialized) map.cameraPosition.bearing else 0f
                updateCompassRotation(mapBearing, azimuthInDegrees)
            }
        }
    }

    private fun updateCompassRotation(mapBearing: Float, deviceAzimuthDegrees: Float = -1f) {
        val azimuth = if (deviceAzimuthDegrees == -1f) {
            Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
        } else {
            deviceAzimuthDegrees
        }

        val compassRotation = mapBearing - azimuth
        RotateAnimation(
            currentDegree,
            -compassRotation,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 250
            fillAfter = true
            compassView.startAnimation(this)
        }
        currentDegree = -compassRotation
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("MapsActivity", "Sensor accuracy changed: ${sensor?.name} to $accuracy")
    }
}