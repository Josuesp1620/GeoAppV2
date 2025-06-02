package com.geosolution.geoapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.geosolution.geoapp.data.local.dao.ClientDao
import com.geosolution.geoapp.data.local.dao.DeviceDataDao
import com.geosolution.geoapp.data.local.dao.UserDao
import com.geosolution.geoapp.data.local.entity.ClientEntity
import com.geosolution.geoapp.data.local.entity.DeviceDataEntity
import com.geosolution.geoapp.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ClientEntity::class,
        DeviceDataEntity::class,
    ],
    exportSchema = false, // Consider setting to true and managing schemas for production
    version = 2, // Incremented version
)
abstract class Database : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val clientDao: ClientDao
    abstract val deviceDataDao: DeviceDataDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 1. Create new table with the correct schema
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `device_data_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `network` INTEGER NOT NULL,
                        `battery` INTEGER NOT NULL,
                        `state` TEXT NOT NULL,
                        `timestamp` TEXT NOT NULL,
                        `bearing` REAL NOT NULL,
                        `latitude` REAL NOT NULL,
                        `longitude` REAL NOT NULL
                    )
                """)

                // 2. Copy data from old table to new table
                // Note: TEXT for timestamp, REAL for bearing, latitude, longitude
                // Old column names: time, angle, latitud, longitud
                database.execSQL("""
                    INSERT INTO `device_data_new` (id, network, battery, state, timestamp, bearing, latitude, longitude)
                    SELECT id, network, battery, state, time, angle, latitud, longitud
                    FROM `device_data`
                """)

                // 3. Drop the old table
                database.execSQL("DROP TABLE `device_data`")

                // 4. Rename the new table to the original table name
                database.execSQL("ALTER TABLE `device_data_new` RENAME TO `device_data`")
            }
        }
    }
}