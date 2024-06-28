package com.geosolution.geoapp.domain.repository

import com.geosolution.geoapp.domain.model.Auth
import com.geosolution.geoapp.domain.model.DeviceData
import kotlinx.coroutines.flow.Flow

interface DeviceDataRepository {
    suspend fun deviceDataSaveStore(deviceData: DeviceData)

    fun deviceDataGetStore() : Flow<List<DeviceData?>>

    fun deviceDataDeleteStore()

}
