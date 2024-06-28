package com.geosolution.geoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geosolution.geoapp.data.local.entity.ClientEntity
import com.geosolution.geoapp.data.local.entity.DeviceDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDataDao {
    @Query("SELECT * FROM device_data")
    fun deviceDataGetAllStore(): Flow<List<DeviceDataEntity>>

    @Query("DELETE FROM device_data")
    fun deviceDataDeleteStore()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun deviceDataSaveStore(client: DeviceDataEntity)
}