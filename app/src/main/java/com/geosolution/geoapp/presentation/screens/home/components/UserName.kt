package com.geosolution.geoapp.presentation.screens.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun UserName(){

    Text(text = buildAnnotatedString {
        append("Hello, ")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
        ) {
            append("\nJosue!")
//            append("\n${viewModel.userName}!")
        }
    },
        modifier = Modifier.fillMaxWidth(),
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        color = DarkGray,
    )
}