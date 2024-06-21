package com.geosolution.geoapp.presentation.screens.map.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.annotations.MarkerOptions

@Composable
fun Map(
    modifier: Modifier = Modifier,
    latLng: LatLng?
) {

    val context = LocalContext.current

    // Initialize MapLibre
    Mapbox.getInstance(context)

    AndroidView(
        factory = {
            MapView(it).apply {
                getMapAsync { mapboxMap ->
                    val customStyle = "https://joucode.ddns.net/google_satelite_hybrid.json"
                    mapboxMap.setStyle(customStyle) { style ->
                        // Agregar marcador después de que el estilo se haya cargado
                        latLng?.let { position ->
                            val markerOptions = MarkerOptions().position(position)
                            mapboxMap.addMarker(markerOptions)
                        }
                    }
                    mapboxMap.cameraPosition = CameraPosition.Builder().target(latLng).zoom(13.0).build()
                }
            }
        },
        modifier = modifier,
        update = { mapView ->
            mapView.getMapAsync { mapboxMap ->
                Log.v("LOCATION_APP_STYLE", "latLng: ${latLng}")
                mapboxMap.cameraPosition = CameraPosition.Builder().target(latLng).zoom(18.0).build()
                val customStyle = "https://joucode.ddns.net/google_satelite_hybrid.json"
                mapboxMap.setStyle(customStyle) { style ->
                    // Remover marcadores existentes para evitar duplicados
                    mapboxMap.clear()
                    latLng?.let { position ->
                        val markerOptions = MarkerOptions().position(position)
                        mapboxMap.addMarker(markerOptions)
                    }
                }
            }
        },
    )
}