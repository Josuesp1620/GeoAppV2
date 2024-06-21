package com.geosolution.geoapp.domain.model

import com.geosolution.geoapp.domain.utils.Domain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class LocationCurrent(
    val latitude: String?,
    val longitude: String?,
    val state: String?,
) : Domain
