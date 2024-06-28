package com.geosolution.geoapp.data.repository

import com.geosolution.geoapp.data.local.dao.DeviceDataDao
import com.geosolution.geoapp.data.local.entity.asDatabaseEntity
import com.geosolution.geoapp.domain.model.DeviceData
import com.geosolution.geoapp.domain.repository.DeviceDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeviceDataRepositoryImpl (
    private val dataBase: DeviceDataDao
): DeviceDataRepository {
    override suspend fun deviceDataSaveStore(deviceData: DeviceData) {
        deviceData.id = 0
        dataBase.deviceDataSaveStore(deviceData.asDatabaseEntity())
    }

    override fun deviceDataGetStore(): Flow<List<DeviceData?>> = dataBase.deviceDataGetAllStore().map {
        list -> list.map {
            it.asDomain()
        }
    }

    override fun deviceDataDeleteStore() = dataBase.deviceDataDeleteStore()

}