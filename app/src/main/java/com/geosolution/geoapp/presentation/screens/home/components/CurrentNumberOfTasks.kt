package com.geosolution.geoapp.presentation.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrentNumberOfTasks(
){
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text =
            if(false) {
                "You currently have  unfinished tasks"
            }
            else{
                "Yay!, You currently don't have any task"
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
        )
    }
}
