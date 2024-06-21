package com.geosolution.geolocation

import androidx.lifecycle.Observer

/**
 * Receives results related to permission model
 */
class PermissionObserver(private val onResult: (Throwable?) -> Unit) : Observer<String> {

    override fun onChanged(value: String) {
        logDebug("Received Permission broadcast")
        isRequestingPermission.set(false)
        when (value) {
            Constants.GRANTED -> {
                logDebug("Permission granted")
                onResult(null)
            }
            else -> {
                logDebug(value)
                onResult(Throwable(value))
            }
        }
        permissionLiveData.removeObserver(this)
    }

}