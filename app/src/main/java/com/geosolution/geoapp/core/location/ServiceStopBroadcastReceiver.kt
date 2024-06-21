package com.geosolution.geoapp.core.location

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.geosolution.geolocation.GeoLocation

/*
 * Created by Birju Vachhani on 23 September 2019
 * Copyright © 2019 locus-android. All rights reserved.
 */

internal class ServiceStopBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "ServiceStopBroadcastReceiver"
    }

    @SuppressLint("LongLogTag")
    override fun onReceive(context: Context, intent: Intent) {
        Log.e(TAG, "Received broadcast to stop location updates")
        GeoLocation.stopLocationUpdates()
        context.stopService(Intent(context, LocationService::class.java))
    }
}