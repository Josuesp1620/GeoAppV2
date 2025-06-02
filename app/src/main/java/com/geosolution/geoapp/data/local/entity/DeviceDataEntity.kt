package com.geosolution.geoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geosolution.geoapp.data.local.utils.DatabaseEntity
import com.geosolution.geoapp.domain.model.DeviceData

@Entity(tableName = "device_data")
data class DeviceDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val network: Int,
    val battery: Int,
    val state: String,
    val timestamp: String, // Renamed from time
    val bearing: Float,    // Renamed from angle
    val latitude: Double,  // Corrected from latitud
    val longitude: Double  // Corrected from longitud
): DatabaseEntity {
    override fun asDomain(): DeviceData = DeviceData(
        id = id,
        network = network,
        battery = battery,
        state = state,
        timestamp = timestamp, // Use new field name
        bearing = bearing,     // Use new field name
        latitude = latitude,   // Use new field name
        longitude = longitude  // Use new field name
    )
}

fun DeviceData.asDatabaseEntity() : DeviceDataEntity = DeviceDataEntity(
    id = id,
    network = network,
    battery = battery,
    state = state,
    timestamp = timestamp, // Use new field name
    bearing = bearing,     // Use new field name
    latitude = latitude,   // Use new field name
    longitude = longitude  // Use new field name
)
