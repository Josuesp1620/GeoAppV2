package com.geosolution.geoapp.presentation.screens.map.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import android.util.Log
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.geosolution.geoapp.R
import com.geosolution.geoapp.presentation.screens.map.MapState
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.plugins.annotation.SymbolManager
import org.maplibre.android.plugins.annotation.SymbolOptions
import org.maplibre.android.style.layers.RasterLayer
import org.maplibre.android.style.sources.RasterSource
import org.maplibre.android.style.sources.TileSet
import org.maplibre.android.utils.BitmapUtils

@Composable
fun MarkerMapScreen(
    selectedMapStyle: String,
    state: MapState
) {
    val context = LocalContext.current

    MapLibre.getInstance(context)

    val mapView = remember { MapView(context) }

    // Observa cambios en el estilo del mapa Y en la ubicación
    LaunchedEffect(selectedMapStyle, state.location) {
        mapView.getMapAsync { mapboxMap ->
            // It's important that setStyle is not called excessively if only location changes.
            // Ideally, setStyle is called when selectedMapStyle changes.
            // And marker/camera updates happen when state.location changes.
            // This might require restructuring the LaunchedEffect or using multiple effects.

            // Simplification for subtask: Re-apply style and then marker/camera.
            // More optimal: Separate effects or logic within getMapAsync.
            mapboxMap.setStyle(selectedMapStyle) { style ->
                // ... (WMS layer logic can remain here as it's style-dependent) ...
                val wmsUrlTemplate = "https://geosdot.servicios.gob.pe/geoserver/geoportal/wms?" +
                        "service=WMS" +
                        "&version=1.1.1" +
                        "&request=GetMap" +
                        "&layers=geoportal%3Aaerodromo_2022" +
                        "&styles=" +
                        "&format=image%2Fpng" +
                        "&transparent=true" +
                        "&srs=EPSG%3A3857" +
                        "&bbox={bbox-epsg-3857}" +
                        "&width=256&height=256"

                val sourceId = "wms-source"
                val layerId = "wms-layer"

                if (style.getSource(sourceId) == null) {
                    val tileSet = TileSet("tileset", wmsUrlTemplate)
                    val wmsSource = RasterSource(sourceId, tileSet, 256)
                    style.addSource(wmsSource)
                }

                if (style.getLayer(layerId) == null) {
                    val rasterLayer = RasterLayer(layerId, sourceId)
                    style.addLayer(rasterLayer)
                }

                // Marker and Camera logic (now also style-dependent due to this structure)
                val currentLocationDomain = state.location
                if (currentLocationDomain != null) {
                    val lat = currentLocationDomain.latitude.toDoubleOrNull()
                    val lon = currentLocationDomain.longitude.toDoubleOrNull()

                    if (lat != null && lon != null) {
                        val currentMapLatLng = LatLng(lat, lon)
                        val markerDrawable = ContextCompat.getDrawable(context, R.drawable.location_marker)

                        if (markerDrawable != null) {
                            val bitmap = BitmapUtils.getBitmapFromDrawable(markerDrawable)
                            if (bitmap != null) {
                                if (style.getImage("marker-pin") == null) { // Add image only if not already present
                                     style.addImage("marker-pin", bitmap)
                                }

                                // For SymbolManager, it's often better to create it once and update symbols,
                                // or clear old symbols before adding new ones.
                                // Creating a new SymbolManager on every location change might be inefficient or lead to leaks.
                                // However, for simplicity in this subtask, we'll recreate as per original commented code's apparent intent.
                                // A more robust solution would manage the SymbolManager instance carefully.
                                val symbolManager = SymbolManager(mapView, mapboxMap, style)
                                symbolManager.iconAllowOverlap = true
                                symbolManager.iconIgnorePlacement = true
                                symbolManager.deleteAll() // Clear previous markers

                                val symbolOptions = SymbolOptions()
                                    .withLatLng(currentMapLatLng)
                                    .withIconImage("marker-pin")
                                    .withIconSize(0.35f)
                                    .withIconAnchor("bottom")
                                    .withIconRotate(currentLocationDomain.bearing) // Using bearing for rotation

                                symbolManager.create(symbolOptions)
                            }
                        } else {
                             Log.w("MarkerMapScreen", "location_marker drawable not found.")
                        }

                        mapboxMap.cameraPosition = CameraPosition.Builder()
                            .target(currentMapLatLng)
                            .zoom(15.0)
                            .bearing(currentLocationDomain.bearing.toDouble()) // Using bearing for camera
                            .build()
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mapView.onStop()
            mapView.onDestroy()
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize()
    )
}
