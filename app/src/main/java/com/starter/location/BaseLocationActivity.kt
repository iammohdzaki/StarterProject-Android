package com.starter.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.os.PersistableBundle
import android.provider.Settings
import com.google.android.gms.location.*
import com.starter.R
import com.starter.permissions.BasePermissionActivity


/**
Created by Mohammad Zaki
on Oct,19 2021
 **/
abstract class BaseLocationActivity : BasePermissionActivity() {

    private val PERMISSION_REQUEST_CODE = 2001

    private var locationProvider: FusedLocationProviderClient? = null
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        locationProvider = LocationServices.getFusedLocationProviderClient(this)
    }

    open fun requestCurrentLocation(rationalMessage: String) {
        requestPermission(PERMISSION_REQUEST_CODE, rationalMessage, permissions)
    }

    @SuppressLint("MissingPermission")
    private fun requestLastLocation() {
        locationProvider?.lastLocation?.addOnSuccessListener(this) { location ->
            if (location != null) {
                onLocationReceived(location)
            } else {
                requestLocationUpdate()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdate() {
        var locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5
            fastestInterval = 0
            numUpdates = 1
        }

        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        locationProvider?.requestLocationUpdates(
            locationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private var mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            onLocationReceived(mLastLocation)
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    override fun onPermissionDenied(requestCode: Int, perms: List<String?>?) {
        showToast("This permission is required! Features will not work!")
        requestPermission(
            PERMISSION_REQUEST_CODE,
            getString(R.string.rational_msg_location_permissions),
            permissions
        )
    }

    override fun onPermissionGranted(requestCode: Int, perms: List<String?>?) {
        if (isLocationEnabled()) {
            requestLastLocation()
        } else {
            showToast("Please turn on your location...")
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    override fun onPermissionPermanentlyDenied(requestCode: Int, perms: List<String?>) {
        showToast("Please turn on your location...")
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    fun stopLocationUpdates() {
        if (mLocationCallback != null) {
            locationProvider?.removeLocationUpdates(mLocationCallback)
        }
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }

    protected abstract fun onLocationReceived(location: Location?)

}