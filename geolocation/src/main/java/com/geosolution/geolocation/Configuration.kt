package com.geosolution.geolocation

import android.os.Parcelable
import com.geosolution.geolocation.Configuration.Companion.FASTEST_INTERVAL_IN_MS
import com.geosolution.geolocation.Configuration.Companion.INTERVAL_IN_MS
import com.geosolution.geolocation.Configuration.Companion.MAX_WAIT_TIME_IN_MS
import com.google.android.gms.location.Priority
import kotlinx.parcelize.Parcelize
import com.google.android.gms.location.LocationRequest as GMSLocationRequest
import com.huawei.hms.location.LocationRequest as HMSLocationRequest


/**
 * Data class to store location related configurations which includes dialog messages and instance
 * of LocationRequest class.
 */
@GeoLocationMarker
@Parcelize
data class Configuration(
    private var locationRequestConfiguration: LocationRequestConfiguration = LocationRequestConfiguration(),
    internal var locationRequest: GeoLocationLocationRequest? = null,
    var shouldResolveRequest: Boolean = true,
    var enableBackgroundUpdates: Boolean = false,
    var forceBackgroundUpdates: Boolean = false,
) : Parcelable {

    companion object {
        internal const val INTERVAL_IN_MS = 20000L
        internal const val FASTEST_INTERVAL_IN_MS = 1000L
        internal const val MAX_WAIT_TIME_IN_MS = 1000L
    }

    /**
     * Creates an instance of LocationRequest class.
     * @param func is a LocationRequest's lambda receiver which provide a block to configure
     * LocationRequest.
     */
    fun request(func: (@GeoLocationMarker LocationRequestConfiguration).() -> Unit) {
        locationRequestConfiguration = LocationRequestConfiguration().apply(func)
    }

    /**
     * With the help of this method we will set the value of [locationRequest] With respect to
     * [locationRequestConfiguration].
     */
    internal fun setLocationRequest(availableService: AvailableService) {
        locationRequest = when (availableService) {
            AvailableService.HMS -> GeoLocationLocationRequest.GeoLocationHMSLocationRequest(
                HMSLocationRequest()
                    .setInterval(locationRequestConfiguration.interval)
                    .setPriority(locationRequestConfiguration.priority)
                    .setFastestInterval(locationRequestConfiguration.fastestInterval)
                    .setExpirationDuration(locationRequestConfiguration.expirationTime)
                    .setNumUpdates(locationRequestConfiguration.numUpdates)
                    .setSmallestDisplacement(locationRequestConfiguration.smallestDisplacement)
                    .setMaxWaitTime(locationRequestConfiguration.maxWaitTime)
            )

            AvailableService.GMS -> GeoLocationLocationRequest.GeoLocationGMSLocationRequest(
                GMSLocationRequest.Builder(locationRequestConfiguration.interval)
                    .setPriority(locationRequestConfiguration.priority)
                    .setMinUpdateIntervalMillis(locationRequestConfiguration.fastestInterval)
                    .setDurationMillis(locationRequestConfiguration.expirationTime)
                    .setMaxUpdates(locationRequestConfiguration.numUpdates)
                    .setMinUpdateDistanceMeters(locationRequestConfiguration.smallestDisplacement)
                    .setMaxUpdateDelayMillis(locationRequestConfiguration.maxWaitTime)
                    .build()
            )

            AvailableService.NONE -> null
        }
    }
}

/**
 * This data class holds the configurations of a LocationRequest.
 * Note that the location request will be defined in the runtime so the passed configurations should
 * be balanced between the Huawei Mobile Service and the Google Mobile Service LocationRequest.
 *
 * Please refer to the documentations for more details.
 *
 * -- gms LocationRequest --
 *
 * https://developers.google.com/android/reference/com/google/android/gms/location/LocationRequest
 *
 * -- hms LocationRequest --
 *
 * https://developer.huawei.com/consumer/en/doc/development/HMSCore-References/locationrequest-0000001050986189
 *
 * @property priority is an instance of Int, Which defines the the priority of the location request.
 *
 * Please choose a valid option between :
 * [Priority.PRIORITY_HIGH_ACCURACY] Constant Value: 102, Requests a tradeoff that is balanced
 * between location accuracy and power usage.
 *
 * [Priority.PRIORITY_BALANCED_POWER_ACCURACY] Constant Value: 100, Requests a tradeoff that favors
 * highly accurate locations at the possible expense of additional power usage.
 *
 * [Priority.PRIORITY_LOW_POWER] Constant Value: 104, Requests a tradeoff that favors low power
 * usage at the possible expense of location accuracy.
 *
 * [Priority.PRIORITY_PASSIVE] Constant Value: 105, Ensures that no extra power will be used to
 * derive locations. This enforces that the request will act as a passive listener that will only
 * receive "free" locations calculated on behalf of other clients, and no locations will be
 * calculated on behalf of only this request.
 *
 * Note that it will throw an [IllegalArgumentException] if the passed value is not valid.
 *
 * The default value is [Priority.PRIORITY_BALANCED_POWER_ACCURACY].
 *
 * @property interval is an instance of Long, Which is the time between receiving each location
 * updates in milliseconds. The Default value is 1000L [INTERVAL_IN_MS].
 * Note that [interval] should be bigger than 0 otherwise it throws [IllegalArgumentException].
 *
 * @property fastestInterval is an instance of Long, Which is the fastest allowed interval of
 * location updates. The location updates may arrive faster than the desired [interval], but will
 * never arrive faster than specified here. The Default value is 1000L [FASTEST_INTERVAL_IN_MS].
 * Note that [fastestInterval] must be greater than or equal to 0 otherwise it throws [IllegalArgumentException].
 *
 * @property expirationTime is an instance of Long, Which is the duration of this request. A location request will not
 * receive any locations after it has expired, and will be removed shortly thereafter.
 * The Default value is [Long.MAX_VALUE].
 * Note that [expirationTime] must be greater than 0 otherwise throws [IllegalArgumentException]
 *
 * @property numUpdates is an instance of Int, Which is the number of the location updates that we
 * want to receive after that the LocationRequest will be removed. The Default value is [Int.MAX_VALUE].
 * Note that [numUpdates] should be bigger than 0 otherwise it throws [IllegalArgumentException].
 *
 * @property smallestDisplacement is an instance of Float, Which is the minimum distance required
 * between consecutive location updates. If a derived location update is not at least the specified
 * distance away from the previous location update delivered to the client, it will not be delivered.
 * The Default value is 0.
 * Note that [smallestDisplacement] should not be smaller than 0.0F otherwise it throws [IllegalArgumentException].
 *
 * @property maxWaitTime is an instance of Long, Which Sets the longest a location update may be
 * delayed. This parameter controls location batching behavior. If this is set to a value at least
 * 2x larger than the [interval], then a device may (but is not required to) save power by
 * delivering locations in batches.
 * The Default value is 1000L [MAX_WAIT_TIME_IN_MS].
 * Note This setting may lead to slow location callbacks. You are advised not to set this parameter
 * unless necessary.
 * Note that [maxWaitTime] must be greater than or equal to 0 otherwise it throws [IllegalArgumentException].
 */
@Parcelize
data class LocationRequestConfiguration(
    var priority: Int = Priority.PRIORITY_HIGH_ACCURACY,
    var interval: Long = INTERVAL_IN_MS,
    var fastestInterval: Long = FASTEST_INTERVAL_IN_MS,
    var expirationTime: Long = Long.MAX_VALUE,
    var numUpdates: Int = Int.MAX_VALUE,
    var smallestDisplacement: Float = 0.0F,
    var maxWaitTime: Long = MAX_WAIT_TIME_IN_MS,
) : Parcelable
