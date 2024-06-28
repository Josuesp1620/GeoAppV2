package com.geosolution.geoapp.domain.model

import com.geosolution.geoapp.domain.utils.Domain
import kotlinx.serialization.Serializable

@Serializable
data class DeviceData(
    var id: Int,
    val name: String,
    val network: Int,
    val battery: Int,
    val state: String,
    val team: String,
    val gender: String,
    val time: String,
    val angle: Float,
    val latitud: Double,
    val longitud: Double
): Domain