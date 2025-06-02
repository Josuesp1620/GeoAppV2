package com.geosolution.geoapp.domain.model

import com.geosolution.geoapp.domain.utils.Domain
import kotlinx.serialization.Serializable

@Serializable
data class DeviceData(
    var id: Int,
    val network: Int,
    val battery: Int,
    val state: String,
    val timestamp: String, // Renamed from time, type String
    val bearing: Float,    // Renamed from angle, type Float
    val latitude: Double,  // Corrected from latitud
    val longitude: Double  // Corrected from longitud
): Domain