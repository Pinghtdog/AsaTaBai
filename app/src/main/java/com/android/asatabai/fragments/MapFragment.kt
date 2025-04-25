package com.android.asatabai.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.asatabai.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    // Cebu area bounds (adjust if needed)
    private val cebuBounds = LatLngBounds(
        LatLng(9.9410, 123.1690), // Southwest corner
        LatLng(11.3775, 124.0963) // Northeast corner
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Move camera to Cebu with padding
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(cebuBounds, 100))
    }
}
