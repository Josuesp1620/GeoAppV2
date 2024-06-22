package com.geosolution.geoapp.presentation.screens.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.geosolution.geoapp.presentation.common.connectivity.NetworkTracker
import com.geosolution.geoapp.presentation.screens.auth.signin.SignInScreen
import com.geosolution.geoapp.presentation.screens.auth.singup.SignUpScreen
import com.geosolution.geoapp.presentation.screens.auth.started.StartedPageScreen
import com.geosolution.geoapp.presentation.screens.client.CreateClientScreen
import com.geosolution.geoapp.presentation.screens.home.HomeScreen
import com.geosolution.geoapp.presentation.screens.main.viewmodel.AuthState
import com.geosolution.geoapp.presentation.screens.map.MapScreen

@Composable
fun Navigation(
    authState: AuthState,
    networkState: NetworkTracker.State,
    navigationController : NavHostController,
) {
    NavHost(navController = navigationController, startDestination = NavScreen.StartedPageScreen.route) {
        composable(route = NavScreen.StartedPageScreen.route) {
            StartedPageScreen(authState, navigationController)
        }
        composable(route = NavScreen.HomeScreen.route) {
            HomeScreen()
        }
        composable(route = NavScreen.MapScreen.route) {
            MapScreen(navigationController)
        }
        composable(route = NavScreen.CreateClientScreen.route) {
            CreateClientScreen(navigationController)
        }
        composable(route = NavScreen.SignInScreen.route) {
            SignInScreen(navigationController)
        }
        composable(route = NavScreen.SignUpScreen.route) {
            SignUpScreen(navigationController)
        }
    }
}