package com.geosolution.geoapp.presentation.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.geosolution.geoapp.presentation.ui.theme.GeoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import org.maplibre.android.MapLibre
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapLibre.getInstance(this)

        setContent {

            GeoAppTheme(dynamicColor = false, darkTheme = false) {
                MainScreen()
            }
        }
    }
}
