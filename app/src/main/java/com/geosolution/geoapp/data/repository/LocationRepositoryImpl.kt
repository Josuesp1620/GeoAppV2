package com.geosolution.geoapp.data.repository

import com.geosolution.geoapp.data.local.datastore.LocationlDataStore
import com.geosolution.geoapp.data.local.entity.asDatabaseEntity
import com.geosolution.geoapp.domain.model.Location
import com.geosolution.geoapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val dataStore: LocationlDataStore
) : LocationRepository {
    override suspend fun locationSaveCache(location: Location)  = dataStore.locationSaveCache(location.asDatabaseEntity())

    override fun locationGetCache(): Flow<Location?>  = dataStore.locationGetCache().map { it?.asDomain() }
    override suspend fun deleteLocationCache() = dataStore.locationDeleteCache()
}