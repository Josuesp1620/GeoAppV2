package com.geosolution.geoapp.data.repository

import com.geosolution.geoapp.data.local.datastore.LocationCurrentLocalDataStore
import com.geosolution.geoapp.data.local.entity.asDatabaseEntity
import com.geosolution.geoapp.domain.model.Location
import com.geosolution.geoapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationCurrentLocalDataStore: LocationCurrentLocalDataStore
) : LocationRepository {
    override suspend fun saveLocationCurrentCache(locationCurrent: Location) = locationCurrentLocalDataStore.save(locationCurrent.asDatabaseEntity())

    override suspend fun getLocationCurrentCache() : Flow<Location?> = locationCurrentLocalDataStore.getLocationCurrent().map { it?.asDomain() }
}