package com.geosolution.geoapp.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosolution.geoapp.R

@Composable
fun WeatherInfo(
    temperature : Double?,
    humidity: Double?,
    precipitation: Double?,
    weatherType: String,
    weatherIcon: Int
) {
    Row(modifier = Modifier
        .width(280.dp)
        .height(80.dp)
        .padding(20.dp, 0.dp, 20.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {

        Column(modifier = Modifier.height(70.dp),
            verticalArrangement = Arrangement.SpaceBetween) {
            InfoWithIcon(info = "  Temperature: $temperature °C", icon = painterResource(id = R.drawable.ic_thermo))
            InfoWithIcon(info = "  Humidity: $humidity %", icon = painterResource(id = R.drawable.ic_humidity))
            InfoWithIcon(info = "  Precipitation: $precipitation mm", icon = painterResource(id = R.drawable.ic_precipitation))
        }

        Column(
            modifier = Modifier.padding(PaddingValues(start = 10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                modifier = Modifier.scale(1f),
                painter = painterResource(id = weatherIcon),
                contentDescription = null
            )

            Text(text = weatherType,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp)
        }
    }
}