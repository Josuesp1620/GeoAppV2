package com.geosolution.geoapp.domain.model

import com.geosolution.geoapp.domain.utils.Domain
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String,
    val fullname : String,
    val username : String,
    val avatar : String?
): Domain