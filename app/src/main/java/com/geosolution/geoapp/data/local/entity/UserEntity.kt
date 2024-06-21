package com.geosolution.geoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geosolution.geoapp.data.local.utils.DatabaseEntity
import com.geosolution.geoapp.domain.model.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val full_name: String,
    val name: String,
    val image: String,
    val gender: String,
): DatabaseEntity {
    override fun asDomain(): User = User(
        id = id,
        full_name = full_name,
        name = name,
        image = image,
        gender = gender
    )
}

fun User.asDatabaseEntity() : UserEntity = UserEntity(
    id = id,
    full_name = full_name,
    name = name,
    image = image,
    gender = gender
)