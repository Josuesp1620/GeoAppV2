package com.geosolution.geoapp.data.local.entity

import com.geosolution.geoapp.data.local.utils.DatabaseEntity
import com.geosolution.geoapp.domain.model.LocationCurrent
import kotlinx.serialization.Serializable

data class LocationCurrentEntity(
    val latitude: String?,
    val longitude: String?,
    val state: String?,
): DatabaseEntity {
    override fun asDomain(): LocationCurrent = LocationCurrent(
        latitude= latitude,
        longitude= longitude,
        state= state,
    )
}

fun LocationCurrent.asDatabaseEntity() : LocationCurrentEntity = LocationCurrentEntity(
    latitude= latitude,
    longitude= longitude,
    state= state,
)
