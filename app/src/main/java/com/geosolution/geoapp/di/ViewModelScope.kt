package com.geosolution.geoapp.di

import com.geosolution.geoapp.domain.repository.AuthRepository
import com.geosolution.geoapp.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelScope {

    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCacheAuthUseCase(authRepository: AuthRepository): CacheAuthUseCase {
        return CacheAuthUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCacheAuthUseCase(authRepository: AuthRepository): GetCacheAuthUseCase {
        return GetCacheAuthUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun providesCacheLocationCurrentUseCase(locationRepository: LocationRepository): CacheLocationCurrentUseCase {
        return CacheLocationCurrentUseCase(locationRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCacheLocationCurrentUseCase(locationRepository: LocationRepository): GetCacheLocationCurrentUseCase {
        return GetCacheLocationCurrentUseCase(locationRepository)
    }
}
