package com.geosolution.geoapp.presentation.screens.main.viewmodel

import androidx.compose.runtime.Immutable
import com.geosolution.geoapp.domain.model.Auth
import com.geosolution.geoapp.domain.model.User
import com.geosolution.geoapp.presentation.common.connectivity.NetworkTracker

@Immutable
data class MainState(
    val user: User? = null,
    val authState: AuthState = AuthState.Loading,
    val networkState: NetworkTracker.State = NetworkTracker.Init,
    val snackbar: String = ""
)

sealed class AuthState {

    object Loading : AuthState()

    data class Authenticated(val auth: Auth) : AuthState()

    object Unauthenticated : AuthState()
}
