package com.geosolution.geoapp.presentation.screens.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.geosolution.geoapp.R
import com.geosolution.geoapp.presentation.ui.theme.PrimaryColor

@SuppressLint("UnrememberedMutableState")
@Composable
fun TopBar(
    navigationController: NavController,
    topBarTitle : String,
) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(
            when (navigationController.currentDestination?.route) {
                "home_screen" -> {
                    PrimaryColor
                }
                else -> {
                    Color.White
                }
            }
        )
        .padding(20.dp, 20.dp, 20.dp, 10.dp)
        .height(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {

        Image(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = null)

        Text(
            text = topBarTitle,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp)

        Image(
            modifier = Modifier
                .clickable(onClick = {
                    if(navigationController.currentDestination?.route == "home_screen") {
                        navigationController.navigate("calendar_screen")
                    } else {
                        navigationController.navigate("home_screen")
                    }
                }),
            painter = if(topBarTitle == "HOME") painterResource(id = R.drawable.ic_map) else painterResource(id = R.drawable.ic_home),
            contentDescription = null)
    }
}