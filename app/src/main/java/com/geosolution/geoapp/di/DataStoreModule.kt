package com.geosolution.geoapp.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.geosolution.geoapp.data.local.datastore.AuthDataStore
import com.geosolution.geoapp.data.local.datastore.LocationlDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideAuthLocalDataStore(@ApplicationContext context: Context): AuthDataStore {
        return AuthDataStore(dataStore = context.authStore)
    }

    @Provides
    @Singleton
    fun provideLocationCurrentLocalDataStore(@ApplicationContext context: Context): LocationlDataStore {
        return LocationlDataStore(dataStore = context.locationStore)
    }

}


private val Context.authStore by preferencesDataStore("auth")

private val Context.locationStore by preferencesDataStore("location")

