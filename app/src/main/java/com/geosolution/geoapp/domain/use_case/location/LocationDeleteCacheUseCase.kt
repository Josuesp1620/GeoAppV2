package com.geosolution.geoapp.domain.use_case.location

import com.geosolution.geoapp.domain.repository.LocationRepository
import javax.inject.Inject

class LocationDeleteCacheUseCase @Inject constructor(
    private val repository: LocationRepository
){
    suspend operator fun invoke() = repository.deleteLocationCache()
}