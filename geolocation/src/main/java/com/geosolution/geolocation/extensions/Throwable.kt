package com.geosolution.geolocation.extensions

import com.geosolution.geolocation.Constants

// Extension property to check if the error caused because the user denied permission or not
val Throwable.isDenied get() = this.message == Constants.DENIED

// Extension property to check if the error caused because the user permanently denied permission or not
val Throwable.isPermanentlyDenied get() = this.message == Constants.PERMANENTLY_DENIED

// Extension property to check if the error caused because the location settings resolution failed or not
val Throwable.isSettingsResolutionFailed get() = this.message == Constants.RESOLUTION_FAILED

// Extension property to check if the error caused because the user denied enabling location or not
val Throwable.isSettingsDenied get() = this.message == Constants.LOCATION_SETTINGS_DENIED

// Extension property to check if the error caused because of some Fatal Exception or not
val Throwable.isFatal get() = !isDenied && !isPermanentlyDenied && !isSettingsDenied && !isSettingsResolutionFailed
