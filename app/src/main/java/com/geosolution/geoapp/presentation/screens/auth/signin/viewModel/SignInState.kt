package com.geosolution.geoapp.presentation.screens.auth.signin.viewModel

import com.geosolution.geoapp.domain.model.User

data class SignInState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val authenticated: Boolean = false
) {
    val isFilled get() = email.isNotBlank() && password.isNotBlank()
}
