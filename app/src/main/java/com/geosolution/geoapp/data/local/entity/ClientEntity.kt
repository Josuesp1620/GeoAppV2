package com.geosolution.geoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geosolution.geoapp.data.local.utils.DatabaseEntity
import com.geosolution.geoapp.domain.model.Client

@Entity(tableName = "client")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: String?,
    val name: String?,
    val fullName: String?,
    val vat: String?,
    val businessName: String?,
    val address: String?,
    val coordinates: String?,
    val image: String?
): DatabaseEntity {
    override fun asDomain(): Client = Client(
        id = id,
        name = name,
        fullName = fullName,
        vat = vat,
        businessName = businessName,
        address = address,
        coordinates = coordinates,
        image = image,
    )
}

fun Client.asDatabaseEntity() : ClientEntity = ClientEntity(
    id = id,
    name = name,
    fullName = fullName,
    vat = vat,
    businessName = businessName,
    address = address,
    coordinates = coordinates,
    image = image,
)