package com.geosolution.geolocation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.android.gms.location.LocationRequest as GMSLocationRequest
import com.huawei.hms.location.LocationRequest as HMSLocationRequest

/**
 * [GeoLocationLocationRequest]
 */
@Parcelize
sealed class GeoLocationLocationRequest : Parcelable {

    /**
     * This data class holds an instance of [HMSLocationRequest] also extends [GeoLocationLocationRequest].
     */
    data class GeoLocationHMSLocationRequest(val locationRequest: HMSLocationRequest) :
        GeoLocationLocationRequest()

    /**
     * This data class holds an instance of [GMSLocationRequest] also extends [GeoLocationLocationRequest].
     */
    data class GeoLocationGMSLocationRequest(val locationRequest: GMSLocationRequest) :
        GeoLocationLocationRequest()

    companion object {

        /**
         * [checkAvailableService]
         */
        fun GeoLocationLocationRequest?.checkAvailableService(
            onGMSAvailable: () -> Unit,
            onHMSAvailable: () -> Unit,
            onNoServiceValid: () -> Unit = {
                logError("The Location Request is null, and No service is available.")
            },
        ) = when (this) {
            is GeoLocationGMSLocationRequest -> onGMSAvailable()
            is GeoLocationHMSLocationRequest -> onHMSAvailable()
            null -> onNoServiceValid()
        }
    }
}

/**
 * This enum class defines which services is available, Google mobile service or Huawei Mobile
 * service or none of them.
 */
enum class AvailableService {
    HMS, GMS, NONE
}
