package com.geosolution.geoapp.di

import android.content.Context
import com.geosolution.geoapp.data.common.HttpClient
import com.geosolution.geoapp.presentation.common.connectivity.NetworkTracker
import com.geosolution.geoapp.presentation.common.connectivity.NetworkTrackerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    fun provideNetworkTracker(@ApplicationContext context: Context): NetworkTracker {
        return NetworkTrackerImpl(context)
    }

    @Provides
    @Singleton
    fun provideHttpClient(/*authLocalDataSource: AuthLocalDataSource*/): HttpClient {
        return HttpClient()
    }

}