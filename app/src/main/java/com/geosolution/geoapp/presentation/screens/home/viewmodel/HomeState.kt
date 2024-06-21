package com.geosolution.geoapp.presentation.screens.home.viewmodel

data class HomeState(
    val isLoading: Boolean = true,
    val isAuth: Boolean = false,
    val isLocationCurrent: Boolean = false,
    val snackbar: String = "",
)