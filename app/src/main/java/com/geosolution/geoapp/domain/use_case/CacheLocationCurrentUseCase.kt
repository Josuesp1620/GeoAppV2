package com.geosolution.geoapp.domain.use_case

import com.geosolution.geoapp.domain.model.LocationCurrent
import com.geosolution.geoapp.domain.repository.LocationCurrentRepository
import javax.inject.Inject

class CacheLocationCurrentUseCase @Inject constructor(
    private val locationCurrentRepository: LocationCurrentRepository
) {
    suspend operator fun invoke(location: LocationCurrent) = locationCurrentRepository.saveLocationCurrentCache(location)
}