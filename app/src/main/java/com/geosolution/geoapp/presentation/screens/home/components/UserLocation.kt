package com.geosolution.geoapp.presentation.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserLocation(
    userLocation : String
) {
    Row(modifier = Modifier
        .padding(20.dp, 10.dp, 0.dp, 0.dp)
        .width(250.dp)) {
        Text(
            text = "Weather today at $userLocation:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
        )
    }
}