package com.geosolution.geoapp.data.local.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.geosolution.geoapp.data.local.entity.LocationCurrentEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationCurrentLocalDataStore(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun save(locationCurrent: LocationCurrentEntity) {
        Log.d("LOCATION_SAVECACHE", locationCurrent.toString())
        dataStore.edit { pref ->
            pref[KeyLocationCurrentLatitude] = locationCurrent.latitude.toString()
            pref[KeyLocationCurrentLongitude] = locationCurrent.longitude.toString()
            pref[KeyLocationCurrentState] = locationCurrent.state.toString()
        }

    }

    fun getLocationCurrent(): Flow<LocationCurrentEntity?> {

        return dataStore.data.map { pref ->
            val latitude = pref[KeyLocationCurrentLatitude] ?: return@map null
            val longitude = pref[KeyLocationCurrentLongitude] ?: return@map null
            val state = pref[KeyLocationCurrentState] ?: return@map null

            LocationCurrentEntity(
                longitude = longitude,
                latitude = latitude,
                state = state,
            )
        }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    companion object {
        private val KeyLocationCurrentLatitude = stringPreferencesKey("location_current_latitude")
        private val KeyLocationCurrentLongitude = stringPreferencesKey("location_current_longitude")
        private val KeyLocationCurrentState = stringPreferencesKey("location_current_state")


    }
}