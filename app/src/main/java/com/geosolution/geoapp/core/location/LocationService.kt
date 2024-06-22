package com.geosolution.geoapp.core.location

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.geosolution.geoapp.R
import com.geosolution.geolocation.GeoLocation
import com.geosolution.geolocation.GeoLocationResult

class LocationService : LifecycleService() {

    companion object {
        const val NOTIFICATION_ID = 787
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    private val manager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            ?: throw Exception("No notification manager found")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.e("BIRJU", "Temp Service started")
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return START_NOT_STICKY
    }

    private fun stop() {
        GeoLocation.stopLocationUpdates()
        this@LocationService.stopService(Intent(this@LocationService, LocationService::class.java))
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun start() {
        startForeground(NOTIFICATION_ID, getNotification())
        GeoLocation.configure {
            enableBackgroundUpdates = true
        }
        GeoLocation.startLocationUpdates(this).observe(this) { result ->
            manager.notify(NOTIFICATION_ID, getNotification(result))
        }
    }

    private fun getNotification(result: GeoLocationResult? = null): Notification {
        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                ?: throw Exception("No notification manager found")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    "location",
                    "Location Updates",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        return with(NotificationCompat.Builder(this, "location")) {
            setContentTitle("Location Service")
            result?.apply {
                location?.let {
                    setContentText("Sharing Current Location")
                } ?: setContentText("Error: ${error?.message}")
            } ?: setContentText("Trying to get location updates")
            setSmallIcon(R.drawable.ic_location)
            setAutoCancel(false)
            setOnlyAlertOnce(true)
            build()
        }
    }
}