package com.geosolution.geoapp.presentation.screens.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geosolution.geoapp.presentation.screens.auth.signin.SignInScreen
import com.geosolution.geoapp.presentation.screens.auth.singup.SignUpScreen
import com.geosolution.geoapp.presentation.screens.auth.started.StartedPageScreen
import com.geosolution.geoapp.presentation.screens.home.HomeScreen

@Composable
fun Navigation(
    navigationController : NavHostController = rememberNavController(),
) {
    NavHost(navController = navigationController, startDestination = NavScreen.StartedPageScreen.route) {
        composable(route = NavScreen.StartedPageScreen.route) {
            StartedPageScreen(navigationController)
        }
        composable(route = NavScreen.HomeScreen.route) {
            HomeScreen(navigationController)
        }
        composable(route = NavScreen.SignInScreen.route) {
            SignInScreen(navigationController)
        }
        composable(route = NavScreen.SignUpScreen.route) {
            SignUpScreen(navigationController)
        }

    }
}