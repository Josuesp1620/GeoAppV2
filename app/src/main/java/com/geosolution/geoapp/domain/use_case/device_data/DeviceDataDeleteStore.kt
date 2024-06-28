package com.geosolution.geoapp.domain.use_case.device_data

import com.geosolution.geoapp.domain.repository.DeviceDataRepository
import javax.inject.Inject

class DeviceDataDeleteStore @Inject constructor(
    private val repository: DeviceDataRepository
) {
    operator fun invoke() = repository.deviceDataDeleteStore()

}