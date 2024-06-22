package com.geosolution.geoapp.domain.use_case.location

import com.geosolution.geoapp.domain.model.Location
import com.geosolution.geoapp.domain.repository.LocationRepository
import javax.inject.Inject

class LocationSaveCacheUseCase @Inject constructor(
    private val repository: LocationRepository
){
    suspend operator fun invoke(location: Location) = repository.locationSaveCache(location)
}