package com.geosolution.geoapp.domain.model

import com.geosolution.geoapp.domain.utils.Domain

data class Auth(
    val accessToken: String?,
    val refreshToken: String?,
) : Domain
