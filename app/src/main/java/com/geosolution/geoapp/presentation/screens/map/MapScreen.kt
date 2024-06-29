package com.geosolution.geoapp.presentation.screens.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.geosolution.geoapp.R
import com.geosolution.geoapp.presentation.screens.map.components.ButtonAction
import com.geosolution.geoapp.presentation.screens.map.components.MarkerMapScreen
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import com.geosolution.geoapp.presentation.ui.theme.CampusXTheme
import com.geosolution.geoapp.presentation.ui.utils.ComposeUtils



import kotlinx.coroutines.delay

@Composable
fun MapScreen(
    mapViewModel: MapViewModel = hiltViewModel(),
    navController: NavController,
) {

    val state by mapViewModel.state.collectAsStateWithLifecycle()

    var isCardVisible by remember { mutableStateOf(false) }
    var selectedMapStyle by remember { mutableStateOf("https://basemaps.cartocdn.com/gl/positron-nolabels-gl-style/style.json") }


    var shouldShowInfoCard by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        delay(ComposeUtils.slideDownInDuration + 200L)
        shouldShowInfoCard = true
    }

    Box(modifier = Modifier.fillMaxSize()) {

        MarkerMapScreen(
            selectedMapStyle,
            state
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

// Botón en la parte superior-derecha
        ButtonAction(
            icon = Icons.Default.Layers,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(start = 5.dp, top = 5.dp),
            onButtonClick = {
                isCardVisible = !isCardVisible
            }
        )
        CardWithMapStyleSelection(
            visible = isCardVisible,
            onMapStyleSelected = { style ->
                selectedMapStyle = style
            }
        )
    }
}


@Composable
fun CardWithMapStyleSelection(
    visible: Boolean,
    onMapStyleSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(
                initialOffsetY = { it } // Aparece desde abajo
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it } // Desaparece hacia abajo
            ) + fadeOut()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tipo de mapa",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MapStyleButton(R.drawable.voyager_labels, "Estándar") {
                            onMapStyleSelected("https://basemaps.cartocdn.com/gl/positron-nolabels-gl-style/style.json")
                        }
                        MapStyleButton(R.drawable.google_satelite, "Satélite") {
                            onMapStyleSelected("https://joucode.ddns.net/google_satelite_hybrid.json")
                        }
                        MapStyleButton(R.drawable.google_streets, "Relieve") {
                            onMapStyleSelected("https://joucode.ddns.net/google_streets.json")
                        }
                    }

                }
            }
        }
    }
}
@Composable
fun MapStyleButton(imageRes: Int, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
        )
        Text(text = label, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
    }
}