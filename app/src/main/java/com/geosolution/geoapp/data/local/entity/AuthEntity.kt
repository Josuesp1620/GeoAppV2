package com.geosolution.geoapp.data.local.entity

import com.geosolution.geoapp.data.local.utils.DatabaseEntity
import com.geosolution.geoapp.domain.model.Auth

data class AuthEntity(
    val profile: Profile,
    val accessToken: String,
    val refreshToken: String,
): DatabaseEntity {
    override fun asDomain(): Auth = Auth(
        profile = profile,
        accessToken = accessToken, 
        refreshToken = refreshToken
    )
}

fun Auth.asDatabaseEntity() : AuthEntity = AuthEntity(
    profile = profile,
    accessToken = accessToken,
    refreshToken = refreshToken
)