package com.geosolution.geoapp.presentation.screens.auth.singup.viewmodel

data class SignUpState(
    val fullname: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val authenticated: Boolean = false,
) {
    val isFilled get() = email.isNotBlank() && password.isNotBlank() && username.isNotBlank() && fullname.isNotBlank()
}