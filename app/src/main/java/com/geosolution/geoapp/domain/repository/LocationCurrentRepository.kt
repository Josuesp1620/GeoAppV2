package com.geosolution.geoapp.domain.repository

import com.geosolution.geoapp.domain.model.LocationCurrent
import kotlinx.coroutines.flow.Flow

interface LocationCurrentRepository {
    suspend fun saveLocationCurrentCache(locationCurrent: LocationCurrent)

    suspend fun getLocationCurrentCache() : Flow<LocationCurrent?>
}