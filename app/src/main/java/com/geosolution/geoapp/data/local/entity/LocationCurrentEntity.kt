package com.geosolution.geoapp.data.local.entity

import com.geosolution.geoapp.data.local.utils.DatabaseEntity
import com.geosolution.geoapp.domain.model.Location

data class LocationCurrentEntity(
    val latitude: String?,
    val longitude: String?,
    val state: String?,
): DatabaseEntity {
    override fun asDomain(): Location = Location(
        latitude= latitude,
        longitude= longitude,
        state= state,
    )
}

fun Location.asDatabaseEntity() : LocationCurrentEntity = LocationCurrentEntity(
    latitude= latitude,
    longitude= longitude,
    state= state,
)
