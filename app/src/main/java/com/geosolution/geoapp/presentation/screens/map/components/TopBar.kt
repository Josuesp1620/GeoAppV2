package com.geosolution.geoapp.presentation.screens.map.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.draw.shadow
import com.geosolution.geoapp.R
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onUpButtonClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onUpButtonClick,
            modifier = Modifier
                .size(32.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = MaterialTheme.shapes.medium,
                    clip = true
                )
                .background(
                    color = MaterialTheme.colorScheme.surface,
                )
                .padding(4.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                contentDescription = "",
                modifier = Modifier,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}