package com.geosolution.geoapp.presentation.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.geosolution.geoapp.R
import com.geosolution.geoapp.presentation.screens.map.components.ButtonAction
import com.geosolution.geoapp.presentation.screens.map.components.InfoCard
import com.geosolution.geoapp.presentation.screens.map.components.Map
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import com.geosolution.geoapp.presentation.ui.theme.CampusXTheme
import com.geosolution.geoapp.presentation.ui.utils.ComposeUtils
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.coroutines.delay

@Composable
fun MapScreen(
    navController: NavController,
) {

    var shouldShowInfoCard by rememberSaveable { mutableStateOf(true) }

    var mapSize by remember { mutableStateOf(Size(0f, 0f)) }
    var mapCenter by remember { mutableStateOf(Offset(0f, 0f)) }

    LaunchedEffect(key1 = Unit) {
        delay(ComposeUtils.slideDownInDuration + 200L)
        shouldShowInfoCard = true
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Map(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    mapSize = size
                    mapCenter = center
                },
            LatLng(-12.046374, -77.042793)
        )

        // Top-Start button
        ButtonAction(
            icon = ImageVector.vectorResource(id = R.drawable.ic_back),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 5.dp, top = 5.dp),
            onButtonClick = {
                navController.navigate(NavScreen.HomeScreen.route)
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
        ) {
            // Bottom-End button
            ButtonAction(
                icon = ImageVector.vectorResource(id = R.drawable.ic_location),
                modifier = Modifier.padding(end = 5.dp, bottom = 5.dp).align(Alignment.End),
                onButtonClick = {
                    navController.navigate(NavScreen.HomeScreen.route)
                }
            )

            // Contenido que aparece abajo (InfoCard) con animación de deslizamiento
            ComposeUtils.SlideDownAnimatedVisibility(
                visible = shouldShowInfoCard
            ) {
                InfoCard()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MapScreenComposable() {
    CampusXTheme {
        Surface {
            MapScreen(rememberNavController())
        }
    }
}
