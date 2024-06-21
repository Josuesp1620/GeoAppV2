package com.geosolution.geoapp.presentation.screens.main

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import com.geosolution.geoapp.core.location.LocationService
import com.geosolution.geoapp.presentation.ui.theme.CampusXTheme
import com.geosolution.geolocation.AvailableService
import com.geosolution.geolocation.GeoLocation
import com.geosolution.geolocation.extensions.getAvailableService
import com.google.android.gms.location.LocationRequest
import dagger.hilt.android.AndroidEntryPoint
import com.huawei.hms.location.LocationRequest as HSMLocationRequest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (getAvailableService()) {
            AvailableService.HMS -> {
                Intent(this, MainActivity::class.java).apply {
                    putExtra("request", HSMLocationRequest().setInterval(1000))
                }
            }

            else -> {
                Intent(this, MainActivity::class.java).apply {
                    putExtra("request", LocationRequest.Builder(1000).build())
                }
            }
        }

        GeoLocation.setLogging(true)

        setContent {

            CampusXTheme(dynamicColor = false, darkTheme = false) {
                MainScreen()
            }
        }
    }
}
