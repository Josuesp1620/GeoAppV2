package com.geosolution.geoapp.domain.use_case.device_data

import com.geosolution.geoapp.domain.model.DeviceData
import com.geosolution.geoapp.domain.repository.DeviceDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeviceDataGetStore @Inject constructor(
    private val repository: DeviceDataRepository
) {
    operator fun invoke(): Flow<List<DeviceData?>> = repository.deviceDataGetStore()

}