package com.geosolution.geoapp.presentation.screens.map.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

@Composable
fun ButtonAction(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onButtonClick: () -> Unit
) {
    IconButton(
        onClick = onButtonClick,
        modifier = modifier
            .clip(CircleShape)
            .size(48.dp)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                clip = true
            )
            .background(
                color = Color.White,
            )
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black
        )
    }
}