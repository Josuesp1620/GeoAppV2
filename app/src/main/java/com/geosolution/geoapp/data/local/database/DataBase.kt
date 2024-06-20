package com.geosolution.geoapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geosolution.geoapp.data.local.dao.UserDao
import com.geosolution.geoapp.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    exportSchema = false, version = 1,

)
abstract class Database : RoomDatabase() {
    abstract val userDao: UserDao
}