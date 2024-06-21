package com.geosolution.geoapp.core.location

import com.google.android.gms.location.LocationCallback

interface LocationTrackingManager {
    fun registerCallback(locationCallback: LocationCallback)

    fun unRegisterCallback(locationCallback: LocationCallback)
}