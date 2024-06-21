package com.geosolution.geoapp.presentation.screens.home

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.geosolution.geoapp.core.location.LocationService
import com.geosolution.geoapp.presentation.common.connectivity.NetworkTracker
import com.geosolution.geoapp.presentation.screens.home.components.CurrentNumberOfTasks
import com.geosolution.geoapp.presentation.screens.home.components.NoAccessDisplay
import com.geosolution.geoapp.presentation.screens.home.components.TopBar
import com.geosolution.geoapp.presentation.screens.home.components.UserLocation
import com.geosolution.geoapp.presentation.screens.home.components.UserName
import com.geosolution.geoapp.presentation.screens.home.components.WeatherInfo
import com.geosolution.geoapp.presentation.screens.home.viewmodel.HomeViewModel
import com.geosolution.geoapp.presentation.screens.main.MainScreen
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import com.geosolution.geoapp.presentation.ui.theme.AppColor
import com.geosolution.geoapp.presentation.ui.theme.CampusXTheme
import com.geosolution.geoapp.presentation.ui.theme.PrimaryColor
import com.geosolution.geolocation.GeoLocation
import com.huawei.hms.framework.common.ContextCompat.startService


@Composable
fun HomeScreen(
//    networkState: () -> NetworkTracker.State,

) {
    val viewModel: HomeViewModel = hiltViewModel()
//    val navController = rememberNavController()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

//    val network_state = networkState()


    BackHandler {
        (context as? Activity)?.finish()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navigationController = rememberNavController(), topBarTitle = "HOME") },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = DarkGray,
                onClick = {
//                    navController.navigate(NavScreen.SignUpScreen.route)
                }) {
                Icon(Icons.Filled.Add, null, tint = Color.White)
            }
        },
        containerColor = Color.White,
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(0.dp)
                .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(PaddingValues(top = 20.dp, bottom = 20.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Column para UserName y CurrentNumberOfTasks
                    Column(
                        modifier = Modifier.weight(1f) // Ocupa todo el espacio restante
                    ) {
                        UserName()
                        Spacer(modifier = Modifier.height(10.dp))
                        CurrentNumberOfTasks()
                    }

                    // Column para el Switch, ocupa solo 100dp de ancho
                    Column(
                        modifier = Modifier
                            .width(100.dp)
                            .fillMaxHeight()
                            .align(Alignment.CenterVertically)
                    ) {
                        Switch(
                            checked = state.isLocationCurrent,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    val intent = Intent(context, LocationService::class.java)
                                    context.startService(intent)
                                } else {
                                    GeoLocation.stopLocationUpdates()
                                }
                                viewModel.updateLocationCurrentState(checked)
                            }
                        )
                    }
                }

            }

            Column(
                modifier = Modifier
                    .shadow(10.dp, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color.White),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                UserLocation("Pueblo Libre, Lima, Peru")
//                WeatherInfo(
//                    precipitation = 10.0,
//                    temperature = 10.0,
//                    weatherIcon = 1,
//                    weatherType = "lluvia",
//                    humidity = 10.0
//                )

//                    when (network_state) {
//                        is NetworkTracker.Unavailable -> {
//                            NoAccessDisplay(
//                                textToDisplay = "No internet connection and\nlocation is not enabled"
//                            )
//                        }
//                        is NetworkTracker.Available, is NetworkTracker.Init -> {
//                            UserLocation(homeViewModel.getLocation())
//
//                            WeatherInfo(
//                                temperature = homeViewModel.getTemperature(),
//                                humidity = homeViewModel.getHumidity(),
//                                precipitation = homeViewModel.getPrecipitation(),
//                                weatherType = homeViewModel.getTypeOfWeather(),
//                                weatherIcon = homeViewModel.getWeatherIcon()
//                            )
//                        }
//                    }
//
            }
        }
        }
    }

@Composable
@Preview(showBackground = true)
fun HomeScreenComposable() {

    CampusXTheme(dynamicColor = false, darkTheme = false) {
        HomeScreen()
    }

}