package com.example.latihanmaps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.latihanmaps.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_container) as SupportMapFragment?
            ?: SupportMapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().replace(R.id.map_container, it).commit()
            }
        mapFragment.getMapAsync(this)

        binding.btnMyLocation.setOnClickListener {
            if (checkLocationPermission()) getCurrentLocation() else requestPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) getCurrentLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMapClickListener(this)

        // Default ke Jakarta jika lokasi belum didapat
        val jakarta = LatLng(-6.2088, 106.8456)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jakarta, 10f))

        if (checkLocationPermission()) {
            mMap.isMyLocationEnabled = true // Menampilkan titik biru bawaan Google
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        binding.progressBar.visibility = View.VISIBLE
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                binding.progressBar.visibility = View.GONE
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    updateMarker(latLng, "Lokasi Saya")
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    getAddressFromLatLng(latLng)
                } else {
                    Toast.makeText(this, "Gagal mendapatkan lokasi. Aktifkan GPS!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateMarker(latLng: LatLng, title: String) {
        currentMarker?.remove()
        currentMarker = mMap.addMarker(MarkerOptions().position(latLng).title(title))
        currentMarker?.showInfoWindow()
    }

    override fun onMapClick(latLng: LatLng) {
        updateMarker(latLng, "Lokasi Terpilih")
        getAddressFromLatLng(latLng)
    }

    private fun getAddressFromLatLng(latLng: LatLng) {
        binding.textAddress.text = "Mencari alamat..."
        try {
            val geocoder = Geocoder(this, Locale("id", "ID"))
            // Gunakan library Geocoder terbaru atau handle secara manual
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val addressLine = addresses[0].getAddressLine(0)
                binding.textAddress.text = addressLine
                currentMarker?.snippet = addressLine
                currentMarker?.showInfoWindow()
            } else {
                binding.textAddress.text = "Alamat tidak ditemukan"
            }
        } catch (e: Exception) {
            Log.e("MAP_ERROR", "Error: ${e.message}")
            binding.textAddress.text = "Koneksi internet bermasalah"
        }
    }

    private fun checkLocationPermission(): Boolean = ContextCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    // Menu Map Type (Normal, Satellite, dll)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.maps_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.normal_map -> mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.satellite_map -> mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.terrain_map -> mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            R.id.hybrid_map -> mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        }
        return true
    }
}
