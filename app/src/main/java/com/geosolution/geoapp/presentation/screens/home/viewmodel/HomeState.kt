package com.geosolution.geoapp.presentation.screens.home.viewmodel

import com.geosolution.geoapp.domain.model.Location
import com.geosolution.geoapp.domain.model.Client
import com.geosolution.geoapp.domain.model.User

data class HomeState(
    val isLoading: Boolean = true,
    val isAuth: Boolean = false,
    val isLocationCurrent: Boolean = false,
    val location: Location?=null,
    val user: User?=null,
    val clientList: List<Client> = emptyList(),
    val snackbar: String = "",
)