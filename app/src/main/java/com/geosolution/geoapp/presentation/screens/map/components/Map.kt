package com.geosolution.geoapp.presentation.screens.map.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView

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
    val sydney = LatLng(state.location?.latitude!!, state.location.longitude)

    // Observa cambios en el estilo del mapa
    LaunchedEffect(selectedMapStyle) {
        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(selectedMapStyle) { style ->
                val drawable = ContextCompat.getDrawable(context, R.drawable.location_marker)
                style.addImage("marker-pin", BitmapUtils.getBitmapFromDrawable(drawable!!)!!)

                val symbolManager = SymbolManager(mapView, mapboxMap, style)
                symbolManager.iconAllowOverlap = true
                symbolManager.iconIgnorePlacement = true

                val symbol = symbolManager.create(
                    SymbolOptions()
                        .withLatLng(sydney)
                        .withIconImage("marker-pin")
                        .withIconSize(0.35f)
                        .withIconAnchor("bottom")
                )
                symbolManager.update(symbol)

                symbolManager.addClickListener {
                    Toast.makeText(context, "Opera house", Toast.LENGTH_LONG).show()
                    true
                }
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

            }
            mapboxMap.cameraPosition = CameraPosition.Builder().target(sydney).zoom(13.0).build()
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
