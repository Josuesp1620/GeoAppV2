package com.geosolution.geoapp.domain.use_case.location

import com.geosolution.geoapp.domain.model.Location
import com.geosolution.geoapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationGetCacheUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    operator fun invoke(): Flow<Location?> = repository.locationGetCache()
}