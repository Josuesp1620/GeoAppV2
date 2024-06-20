package com.geosolution.geoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geosolution.geoapp.data.local.utils.DatabaseEntity
import com.geosolution.geoapp.domain.model.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val fullname: String,
    val username: String,
    val avatar: String
): DatabaseEntity {
    override fun asDomain(): User = User(
        id = id,
        fullname = fullname,
        username = username,
        avatar = avatar
    )
}

fun User.asDatabaseEntity() : UserEntity = UserEntity(
    id = id,
    fullname = fullname,
    username = username,
    avatar =  avatar
)