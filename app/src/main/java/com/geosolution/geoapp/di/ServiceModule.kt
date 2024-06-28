package com.geosolution.geoapp.di

import com.geosolution.geoapp.data.remote.api.auth.AuthService
import com.geosolution.geoapp.data.remote.api.auth.AuthServiceImpl
import com.geosolution.geoapp.core.location.LocationApiService
import com.geosolution.geoapp.core.location.LocationApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(
        client: HttpClient,
    ): AuthService = AuthServiceImpl(
        client = client,
    )

//    @Provides
//    @Singleton
//    fun provideLocationService (
//        client: HttpClient,
//    ): LocationApiService = LocationApiServiceImpl(
//        client = client,
//    )
}