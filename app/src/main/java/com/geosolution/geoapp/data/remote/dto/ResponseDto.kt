package com.geosolution.geoapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto<T>(
    @SerialName("code")
    val code : Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: T?,
)
