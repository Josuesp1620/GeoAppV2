package com.geosolution.geoapp.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.geosolution.geoapp.data.local.entity.LocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationlDataStore(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun locationSaveCache(locationCurrent: LocationEntity) {
        dataStore.edit { pref ->
            pref[KeyLocationCurrentLatitude] = locationCurrent.latitude.toString()
            pref[KeyLocationCurrentLongitude] = locationCurrent.longitude.toString()
        }

    }

    fun locationGetCache(): Flow<LocationEntity?> {

        return dataStore.data.map { pref ->
            val latitude = pref[KeyLocationCurrentLatitude] ?: return@map null
            val longitude = pref[KeyLocationCurrentLongitude] ?: return@map null
            LocationEntity(
                longitude = longitude,
                latitude = latitude,
            )
        }
    }

    suspend fun locationDeleteCache() {
        dataStore.edit { preferences ->
            preferences.remove(KeyLocationCurrentLatitude)
            preferences.remove(KeyLocationCurrentLongitude)
        }
    }

    companion object {
        private val KeyLocationCurrentLatitude = stringPreferencesKey("location_current_latitude")
        private val KeyLocationCurrentLongitude = stringPreferencesKey("location_current_longitude")
    }
}