package com.geosolution.geoapp.domain.use_case

import com.geosolution.geoapp.domain.model.LocationCurrent
import com.geosolution.geoapp.domain.repository.LocationCurrentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCacheLocationCurrentUseCase @Inject constructor(
    private val locationCurrentRepository: LocationCurrentRepository
) {
    suspend operator fun invoke() : Flow<LocationCurrent?> = locationCurrentRepository.getLocationCurrentCache()
}