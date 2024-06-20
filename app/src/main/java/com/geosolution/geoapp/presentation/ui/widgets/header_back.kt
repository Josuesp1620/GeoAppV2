package com.geosolution.geoapp.presentation.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HeaderBack(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Button(
            contentPadding = PaddingValues(0.dp),
            onClick = onBackClick,
            colors =  ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.White,

                )
        ) {
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "keyboard-arrow-left"
            )
            TextCustom(
                text= " Atras",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight(500)
            )
        }
    }

}