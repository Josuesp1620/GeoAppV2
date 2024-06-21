package com.geosolution.geoapp.data.repository

import com.geosolution.geoapp.data.local.datastore.LocationCurrentLocalDataStore
import com.geosolution.geoapp.data.local.entity.asDatabaseEntity
import com.geosolution.geoapp.domain.model.LocationCurrent
import com.geosolution.geoapp.domain.repository.LocationCurrentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationCurrentRepositoryImpl @Inject constructor(
    private val locationCurrentLocalDataStore: LocationCurrentLocalDataStore
) : LocationCurrentRepository {
    override suspend fun saveLocationCurrentCache(locationCurrent: LocationCurrent) = locationCurrentLocalDataStore.save(locationCurrent.asDatabaseEntity())

    override suspend fun getLocationCurrentCache() : Flow<LocationCurrent?> = locationCurrentLocalDataStore.getLocationCurrent().map { it?.asDomain() }
}