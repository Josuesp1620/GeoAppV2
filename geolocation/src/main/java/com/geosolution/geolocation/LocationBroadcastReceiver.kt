package com.geosolution.geolocation

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.geosolution.geolocation.extensions.getAvailableService
import com.google.android.gms.location.LocationResult as GMSLocationResult
import com.huawei.hms.location.LocationResult as HMSLocationResult

/**
 * Receives location broadcasts
 */
internal class LocationBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_PROCESS_UPDATES =
            "com.geosolution.geolocation.LocationProvider.LocationBroadcastReceiver.action.PROCESS_UPDATES"

        fun getPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, LocationBroadcastReceiver::class.java)
            intent.action = ACTION_PROCESS_UPDATES
            val flags =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                else PendingIntent.FLAG_UPDATE_CURRENT
            return PendingIntent.getBroadcast(context, 0, intent, flags)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        logDebug("Received location update broadcast")
        intent ?: return
        when (context?.getAvailableService()) {
            AvailableService.HMS -> extractDataFromHMS(intent)
            else -> extractDataFromGMS(intent)
        }
    }

    /**
     * With the help of this method we will extract data With Huawei mobile service LocationResult.
     */
    private fun extractDataFromHMS(intent: Intent) {
        if (intent.action == ACTION_PROCESS_UPDATES && HMSLocationResult.hasResult(intent)) {
            HMSLocationResult.extractResult(intent).let { result ->
                if (result?.locations?.isNotEmpty() == true) {
                    result.lastLocation?.let {
                        logDebug("Huawei mobile service Received location $it")
                        locationLiveData.postValue(GeoLocationResult.success(it))
                    }
                }
            }
        }
    }

    /**
     * With the help of this method we will extract data With Google mobile service LocationResult.
     */
    private fun extractDataFromGMS(intent: Intent) {
        if (intent.action == ACTION_PROCESS_UPDATES && GMSLocationResult.hasResult(intent)) {
            GMSLocationResult.extractResult(intent).let { result ->
                if (result?.locations?.isNotEmpty() == true) {
                    result.lastLocation?.let {
                        logDebug("Google mobile service Received location $it")
                        locationLiveData.postValue(GeoLocationResult.success(it))
                    }
                }
            }
        }
    }
}