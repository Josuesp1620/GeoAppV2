package com.geosolution.geoapp.di

import com.geosolution.geoapp.data.remote.api.auth.AuthService
import com.geosolution.geoapp.data.remote.api.auth.AuthServiceImpl
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
}