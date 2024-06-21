package com.geosolution.geoapp.presentation.screens.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.geosolution.geoapp.presentation.common.connectivity.LocationTracker
import com.geosolution.geoapp.presentation.ui.theme.AppColor

@Composable
fun LocationStatus(
    stateProvider: () -> LocationTracker.State,
    modifier: Modifier = Modifier
) {
    val state = stateProvider()
    AnimatedVisibility(
        visible = state == LocationTracker.Unavailable,
        modifier = modifier,
        enter = slideInVertically { -it * 2 },
        exit = slideOutVertically(animationSpec = tween(delayMillis = 2000)) { -it * 2 }
    ) {
        val color by animateColorAsState(
            targetValue = when (state) {
                is LocationTracker.Unavailable -> MaterialTheme.colorScheme.error
                is LocationTracker.Available, is LocationTracker.Init -> AppColor.Green500
            }, label = ""
        )
        val height = with(LocalDensity.current) {
            WindowInsets.statusBars
                .getTop(this)
                .toDp() + 56.dp
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(color)
        ) {
            Text(
                text = when (state) {
                    is LocationTracker.Unavailable -> "Location Unavailable"
                    is LocationTracker.Available, is LocationTracker.Init -> "Location Available"
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.Center)
                    .statusBarsPadding(),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}
