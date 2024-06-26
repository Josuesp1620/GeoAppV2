package com.geosolution.geoapp.data.local.entity

import com.geosolution.geoapp.data.local.utils.DatabaseEntity
import com.geosolution.geoapp.domain.model.Location

data class LocationEntity(
    val latitude: String,
    val longitude: String,
): DatabaseEntity {
    override fun asDomain(): Location = Location(
        latitude= latitude,
        longitude= longitude,
    )
}

fun Location.asDatabaseEntity() : LocationEntity = LocationEntity(
    latitude= latitude,
    longitude= longitude,
)
