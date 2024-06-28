package com.geosolution.geoapp.presentation.screens.map.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.geosolution.geoapp.R
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.plugins.annotation.SymbolManager
import org.maplibre.android.plugins.annotation.SymbolOptions
import org.maplibre.android.utils.BitmapUtils
@Composable
fun MarkerMapScreen(
) {
    val MARKER_NAME = "marker-pin"
    val context = LocalContext.current

    // Initialize MapLibre
    MapLibre.getInstance(context)

    val styleUrl = "https://joucode.ddns.net/google_satelite_hybrid.json"

    val mapView = remember {
        MapView(context).apply {
            getMapAsync { mapboxMap ->
                mapboxMap.setStyle(styleUrl) { style ->
                    val drawable = ContextCompat.getDrawable(context, R.drawable.location_marker)
                    style.addImage(MARKER_NAME, BitmapUtils.getBitmapFromDrawable(drawable!!)!!)

                    val symbolManager = SymbolManager(this, mapboxMap, style)
                    symbolManager.iconAllowOverlap = true
                    symbolManager.iconIgnorePlacement = true

                    // Example location - Sydney Opera House
                    val sydney = LatLng(-33.85416325, 151.20916)

                    val symbol = symbolManager.create(
                        SymbolOptions()
                            .withLatLng(sydney)
                            .withIconImage(MARKER_NAME)
                            .withIconSize(1.25f)
                            .withIconAnchor("bottom")
                    )
                    symbolManager.update(symbol)

                    // Add a listener to trigger marker clicks
                    symbolManager.addClickListener {
                        Toast.makeText(context, "Opera house", Toast.LENGTH_LONG).show()
                        true
                    }
                }

                mapboxMap.cameraPosition = CameraPosition.Builder().target(LatLng(-33.85416325, 151.20916)).zoom(13.0).build()
            }
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize()
    )
}
