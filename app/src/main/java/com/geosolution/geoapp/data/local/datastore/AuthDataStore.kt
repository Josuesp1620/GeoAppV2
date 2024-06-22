package com.geosolution.geoapp.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.geosolution.geoapp.data.local.entity.AuthEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthDataStore(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun authSaveCache(auth: AuthEntity) {
        dataStore.edit { pref ->
            pref[KeyAccessToken] = auth.accessToken.toString()
            pref[KeyRefreshToken] = auth.refreshToken.toString()
        }

    }

    fun authGetCache(): Flow<AuthEntity?> {

        return dataStore.data.map { pref ->
            AuthEntity(
                accessToken = pref[KeyAccessToken] ?: return@map null,
                refreshToken = pref[KeyRefreshToken] ?: return@map null,
            )
        }
    }

    suspend fun authDeleteCache() {
        dataStore.edit { it.clear() }
    }

    companion object {
        private val KeyAccessToken = stringPreferencesKey("access_token")
        private val KeyRefreshToken = stringPreferencesKey("refresh_token")
    }
}