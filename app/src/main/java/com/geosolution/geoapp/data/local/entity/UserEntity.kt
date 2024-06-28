package com.geosolution.geoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geosolution.geoapp.data.local.utils.DatabaseEntity
import com.geosolution.geoapp.domain.model.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val fullName: String?,
    val name: String?,
    val email: String?,
    val password: String?,
    val image: String?,
): DatabaseEntity {
    override fun asDomain(): User = User(
        id = id,
        fullName = fullName,
        name = name,
        image = image,
        email = email,
        password = password,
    )
}

fun User.asDatabaseEntity() : UserEntity = UserEntity(
    id = id,
    fullName = fullName,
    name = name,
    image = image,
    email = email,
    password = password,
)