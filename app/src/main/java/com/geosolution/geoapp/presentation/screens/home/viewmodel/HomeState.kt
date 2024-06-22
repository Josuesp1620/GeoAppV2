package com.geosolution.geoapp.presentation.screens.home.viewmodel

import android.location.Location

data class HomeState(
    val isLoading: Boolean = true,
    val isAuth: Boolean = false,
    val isLocationCurrent: Boolean = false,
    val location: Location?=null,
    val snackbar: String = "",
)