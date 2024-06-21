package com.geosolution.geoapp.di

import android.content.Context
import com.geosolution.geoapp.core.location.DefaultLocationTrackingManager
import com.geosolution.geoapp.core.location.LocationTrackingManager
import com.geosolution.geoapp.core.location.LocationUtils
import com.geosolution.geoapp.presentation.common.connectivity.LocationTracker
import com.geosolution.geoapp.presentation.common.connectivity.LocationTrackerImpl
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationTracker(@ApplicationContext context: Context): LocationTracker {
        return LocationTrackerImpl(context)
    }

    @Singleton
    @Provides
    fun provideLocationTrackingManager(
        @ApplicationContext context: Context,
        fusedLocationProviderClient: FusedLocationProviderClient,
    ): LocationTrackingManager {
        return DefaultLocationTrackingManager(
            fusedLocationProviderClient = fusedLocationProviderClient,
            context = context,
            locationRequest = LocationUtils.locationRequestBuilder.build()
        )
    }

}