package com.geosolution.geoapp.domain.model

import com.geosolution.geoapp.domain.utils.Domain
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val full_name: String,
    val gender: String,
    val image: String
) : Domain
