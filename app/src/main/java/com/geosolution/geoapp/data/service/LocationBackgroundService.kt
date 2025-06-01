package com.geosolution.geoapp.data.service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.geosolution.geoapp.R // Make sure this R is correctly resolved
import com.geosolution.geoapp.domain.model.Location
import com.geosolution.geoapp.domain.use_case.location.LocationSaveCacheUseCase
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationBackgroundService : Service() {

    @Inject
    lateinit var locationSaveCacheUseCase: LocationSaveCacheUseCase

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationCallback: LocationCallback
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    companion object {
        const val ACTION_START_LOCATION_SERVICE = "ACTION_START_LOCATION_SERVICE"
        const val ACTION_STOP_LOCATION_SERVICE = "ACTION_STOP_LOCATION_SERVICE"
        private const val NOTIFICATION_CHANNEL_ID = "location_service_channel"
        private const val NOTIFICATION_ID = 1
        private const val LOCATION_UPDATE_INTERVAL = 10000L // 10 seconds
        private const val FASTEST_LOCATION_UPDATE_INTERVAL = 5000L // 5 seconds
        private const val TAG = "LocationBgService"
    }

    override fun onCreate() {
        super.onCreate()
        // fusedLocationClient is injected by Hilt
        createNotificationChannel()
        setupLocationCallback()
        Log.d(TAG, "Service Created")
    }

    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { androidLocation ->
                    Log.d(TAG, "New location: Lat: ${androidLocation.latitude}, Lon: ${androidLocation.longitude}, Bearing: ${androidLocation.bearing}")
                    val location = Location(
                        latitude = androidLocation.latitude.toString(),
                        longitude = androidLocation.longitude.toString(),
                        bearing = androidLocation.bearing
                    )
                    serviceScope.launch {
                        try {
                            locationSaveCacheUseCase(location)
                            Log.d(TAG, "Location saved: $location")
                        } catch (e: Exception) {
                            Log.e(TAG, "Error saving location: ${e.message}", e)
                        }
                    }
                }
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                Log.d(TAG, "Location availability: ${locationAvailability.isLocationAvailable}")
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand received action: ${intent?.action}")
        intent?.action?.let { action ->
            when (action) {
                ACTION_START_LOCATION_SERVICE -> {
                    startForegroundService()
                    startLocationUpdates()
                    Log.d(TAG, "Location service started successfully via action")
                }
                ACTION_STOP_LOCATION_SERVICE -> {
                    Log.d(TAG, "Stopping service via action")
                    stopLocationUpdates()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        stopForeground(STOP_FOREGROUND_REMOVE)
                    } else {
                        @Suppress("DEPRECATION")
                        stopForeground(true)
                    }
                    stopSelf()
                    Log.d(TAG, "Location service stopped successfully via action")
                }
                else -> Log.w(TAG, "Unknown action: $action")
            }
        }
        return START_STICKY
    }

    private fun startForegroundService() {
        val notification = createNotification()
        try {
            startForeground(NOTIFICATION_ID, notification)
            Log.d(TAG, "Started foreground service.")
        } catch (e: Exception) {
            Log.e(TAG, "Error starting foreground service: ${e.message}", e)
             // Fallback or error handling if startForeground fails (e.g., for POST_NOTIFICATIONS permission on Android 13+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "POST_NOTIFICATIONS permission not granted for foreground service.")
                // Notify the user or stop the service if the notification is critical
            }
        }
    }

    private fun createNotification(): Notification {
        // val pendingIntent: PendingIntent =
        //     Intent(this, YourMainActivity::class.java).let { notificationIntent -> // Replace YourMainActivity
        //         PendingIntent.getActivity(this, 0, notificationIntent,
        //             PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        //     }

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("GeoApp Location Service")
            .setContentText("Tracking your location for GeoApp.")
            .setSmallIcon(R.mipmap.ic_launcher) // Ensure this icon exists
            // .setContentIntent(pendingIntent) // Uncomment to open app on notification tap
            .setOngoing(true)
            .setSilent(true) // To make it less intrusive if desired
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannelName = "Location Service Channel"
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                serviceChannelName,
                NotificationManager.IMPORTANCE_LOW // Use LOW to avoid sound/vibration by default
            ).apply {
                description = "Channel for GeoApp background location tracking"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
            Log.d(TAG, "Notification channel created/updated.")
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Location permissions (FINE or COARSE) not granted. Cannot start updates.")
            // This service assumes permissions are granted by the UI before starting.
            // If not, it should stop itself or signal an error.
            stopSelf() // Stop if permissions are missing
            return
        }

        // Use the new Builder pattern for LocationRequest
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_UPDATE_INTERVAL)
            .setMinUpdateIntervalMillis(FASTEST_LOCATION_UPDATE_INTERVAL)
            .build()

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            Log.d(TAG, "Requested location updates with interval: $LOCATION_UPDATE_INTERVAL ms")
        } catch (unlikely: SecurityException) {
            Log.e(TAG, "Lost location permission. Could not request updates. $unlikely")
            stopSelf() // Stop if security exception occurs
        }  catch (e: Exception) {
            Log.e(TAG, "Could not request location updates. Error: ${e.message}", e)
            stopSelf()
        }
    }

    private fun stopLocationUpdates() {
        Log.d(TAG, "Attempting to stop location updates.")
        // Check if fusedLocationClient is initialized before using it
        if (::fusedLocationClient.isInitialized) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Stopped location updates successfully.")
                    } else {
                        Log.e(TAG, "Failed to stop location updates. Exception: ${task.exception?.message}", task.exception)
                    }
                }
        } else {
            Log.w(TAG, "FusedLocationProviderClient not initialized. Cannot stop updates.")
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // Not a bound service
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Location service being destroyed.")
        stopLocationUpdates()
        serviceJob.cancel() // Cancel the coroutine scope
        Log.d(TAG, "Location service destroyed successfully.")
    }
}
