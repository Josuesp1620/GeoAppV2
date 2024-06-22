package com.geosolution.geoapp.domain.model

import com.geosolution.geoapp.domain.utils.Domain

data class User(
    val id: String?,
    val name: String?,
    val fullName: String?,
    val image: String?,
) : Domain
