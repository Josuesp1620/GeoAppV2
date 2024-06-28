package com.geosolution.geoapp.domain.model

import com.geosolution.geoapp.domain.utils.Domain
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int = 0,
    val name: String?,
    val fullName: String?,
    val email: String?,
    val password: String?,
    val image: String?,
) : Domain
