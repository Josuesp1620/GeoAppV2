package com.geosolution.geoapp.presentation.screens.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun NoAccessDisplay(textToDisplay: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = textToDisplay,
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        color = Color(0XFFC1C1C1)
    )
}