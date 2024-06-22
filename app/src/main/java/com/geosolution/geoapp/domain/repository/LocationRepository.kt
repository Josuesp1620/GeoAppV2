package com.geosolution.geoapp.domain.repository

import com.geosolution.geoapp.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun locationSaveCache(location: Location)

    fun locationGetCache() : Flow<Location?>

    suspend fun deleteLocationCache()

}