package com.geosolution.geoapp.core.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
object RunUtils {

    val locationPermissions = mutableListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    ).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            add(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
        }
    }.toTypedArray()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val notificationPermission = Manifest.permission.POST_NOTIFICATIONS

    val allPermissions = mutableListOf<String>().apply {
        addAll(locationPermissions)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(notificationPermission)
        }
    }.toTypedArray()

    fun Context.hasNotificationPermission() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                notificationPermission
            ) == PERMISSION_GRANTED
        } else true


    fun Context.hasLocationPermission() =
        locationPermissions.all {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PERMISSION_GRANTED
        }

    fun Context.hasAllPermission() =
        allPermissions.all {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PERMISSION_GRANTED
        }

    fun Context.openAppSetting() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also(::startActivity)
    }



}