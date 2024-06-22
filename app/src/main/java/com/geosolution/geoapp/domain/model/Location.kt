package com.geosolution.geoapp.domain.model

import com.geosolution.geoapp.domain.utils.Domain

data class Location(
    val latitude: String,
    val longitude: String,
) : Domain
