package com.geosolution.geoapp.presentation.screens.navigations

sealed class NavScreen(val route : String){
    object StartedPageScreen : NavScreen("splash_screen")
    object HomeScreen : NavScreen("home_screen")
    object MapScreen : NavScreen("map_screen")
    object SignInScreen : NavScreen("signin_screen")
    object SignUpScreen : NavScreen("signup_screen")
}