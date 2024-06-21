package com.geosolution.geoapp.presentation.common.connectivity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class LocationTrackerImpl( context:Context ) : LocationTracker {

    private val locationManager = context.getSystemService<LocationManager>()

    @RequiresApi(Build.VERSION_CODES.P)
    override val flow: Flow<LocationTracker.State> = callbackFlow {
        val locationEnable = locationManager?.isLocationEnabled
        if (locationEnable != null) {
            if(locationEnable){
                send(LocationTracker.Available)
            }else{
                send(LocationTracker.Unavailable)
            }
        }else {
            send(LocationTracker.Unavailable)

        }

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // Handle location update
                trySend(LocationTracker.Available)
            }
            override fun onProviderEnabled(provider: String) {
                trySend(LocationTracker.Available)
            }

            override fun onProviderDisabled(provider: String) {
                trySend(LocationTracker.Unavailable)
            }
        }

        if (locationEnable != null) {

            try {
                locationManager?.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0L, 0f, locationListener)
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)

                awaitClose {
                    locationManager?.removeUpdates(locationListener)
                }
            } catch (e: SecurityException) {

                // Emit initial state based on current location availability
                val passiveProvider = locationManager?.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)
                if (passiveProvider == true) {
                    send(LocationTracker.Available)
                } else {
                    send(LocationTracker.Unavailable)
                }
                val gpsProvider = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)
                if (gpsProvider == true) {
                    send(LocationTracker.Available)
                } else {
                    send(LocationTracker.Unavailable)
                }
                val networkProvider = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                if (networkProvider == true) {
                    send(LocationTracker.Available)
                } else {
                    send(LocationTracker.Unavailable)
                }
            }



            awaitClose {
                locationManager?.removeUpdates(locationListener)
            }
        }

    }
        .distinctUntilChanged()

}