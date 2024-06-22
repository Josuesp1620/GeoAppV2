package com.geosolution.geoapp.data.remote.api.auth

import com.geosolution.geoapp.data.remote.dto.Dto
import com.geosolution.geoapp.domain.model.Auth
import com.geosolution.geoapp.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthDto(
    @SerialName("user")
    val user : User,
    @SerialName("accessToken")
    val accessToken : String ,
    @SerialName("refreshToken")
    val refreshToken : String
) : Dto {
    override fun asDomain(): Auth = Auth(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}