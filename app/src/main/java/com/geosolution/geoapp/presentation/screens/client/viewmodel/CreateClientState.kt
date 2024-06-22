package com.geosolution.geoapp.presentation.screens.client.viewmodel

data class CreateClientState(
    val fullname: String = "",
    val username: String = "",
    val email: String = "",
    val loading: Boolean = false,
) {
    val isFilled get() = email.isNotBlank() && username.isNotBlank() && fullname.isNotBlank()
}