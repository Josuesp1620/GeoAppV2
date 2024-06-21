package com.geosolution.geoapp.presentation.screens.map.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.geosolution.geolocation.GeoLocation
import org.maplibre.android.MapLibre
import org.maplibre.android.annotations.MarkerOptions
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView

@Composable
fun Map(
    modifier: Modifier = Modifier,
    latLng: LatLng?
) {

    val context = LocalContext.current

    // Initialize MapLibre
    MapLibre.getInstance(context)

    AndroidView(
        factory = {
            MapView(it).apply {
                getMapAsync { mapboxMap ->
                    val customStyle = "https://joucode.ddns.net/google_satelite_hybrid.json"
                    mapboxMap.setStyle(customStyle) { style ->

                        GeoLocation.// Example of calling getCurrentLocation
                        getCurrentLocation(context) { result ->
                            result.location?.let { location ->
                                LatLng(location.latitude, location.longitude)
                            }.let {position ->
                                val markerOptions = MarkerOptions().position(position)
                                mapboxMap.addMarker(markerOptions)
                            }

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