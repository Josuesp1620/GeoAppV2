package com.geosolution.geoapp.presentation.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.geosolution.geoapp.presentation.screens.map.components.InfoCard
import com.geosolution.geoapp.presentation.screens.map.components.TopBar
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import com.geosolution.geoapp.presentation.ui.theme.CampusXTheme
import com.geosolution.geoapp.presentation.ui.utils.ComposeUtils
import kotlinx.coroutines.delay

@Composable
fun MapScreen(
    navController: NavController,
) {

    var shouldShowInfoCard by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(key1 = Unit) {
        delay(ComposeUtils.slideDownInDuration + 200L)
        shouldShowInfoCard = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        TopBar(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            onUpButtonClick = {
                navController.navigate(NavScreen.HomeScreen.route)
            }
        )

        ComposeUtils.SlideDownAnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            visible = shouldShowInfoCard
        ) {
            InfoCard()
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
