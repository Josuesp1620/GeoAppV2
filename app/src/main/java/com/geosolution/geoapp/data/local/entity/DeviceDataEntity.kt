package com.geosolution.geoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geosolution.geoapp.data.local.utils.DatabaseEntity
import com.geosolution.geoapp.domain.model.DeviceData

@Entity(tableName = "device_data")
data class DeviceDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
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
): DatabaseEntity {
    override fun asDomain(): DeviceData = DeviceData(
        id=id,
        name = name,
        network = network,
        battery = battery,
        state = state,
        team = team,
        gender = gender,
        time = time,
        angle = angle,
        latitud = latitud,
        longitud = longitud,
    )
}

fun DeviceData.asDatabaseEntity() : DeviceDataEntity = DeviceDataEntity(
    id=id,
    name = name,
    network = network,
    battery = battery,
    state = state,
    team = team,
    gender = gender,
    time = time,
    angle = angle,
    latitud = latitud,
    longitud = longitud,
)
