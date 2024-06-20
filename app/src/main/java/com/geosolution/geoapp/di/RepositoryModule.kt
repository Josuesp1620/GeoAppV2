package com.geosolution.geoapp.di

import com.geosolution.geoapp.data.local.AuthLocalDataStore
import com.geosolution.geoapp.data.remote.api.auth.AuthService
import com.geosolution.geoapp.data.repository.AuthRepositoryImpl
import com.geosolution.geoapp.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
        authLocalDataStore: AuthLocalDataStore
    ): AuthRepository = AuthRepositoryImpl(
        authService = authService,
        authLocalDataStore = authLocalDataStore
    )
}