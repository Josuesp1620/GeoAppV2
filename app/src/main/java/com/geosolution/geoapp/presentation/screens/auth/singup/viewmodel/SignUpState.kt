package com.geosolution.geoapp.presentation.screens.auth.singup.viewmodel

data class SignUpState(
    val fullname: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val loading: Boolean = false,
    val authenticated: Boolean = false,
) {
    val isFilled get() = email.isNotBlank() && password.isNotBlank() && name.isNotBlank() && fullname.isNotBlank()
}