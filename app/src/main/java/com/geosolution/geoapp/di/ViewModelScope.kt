package com.geosolution.geoapp.di

import com.geosolution.geoapp.domain.repository.AuthRepository
import com.geosolution.geoapp.domain.repository.LocationCurrentRepository
import com.geosolution.geoapp.domain.use_case.CacheAuthUseCase
import com.geosolution.geoapp.domain.use_case.CacheLocationCurrentUseCase
import com.geosolution.geoapp.domain.use_case.GetCacheAuthUseCase
import com.geosolution.geoapp.domain.use_case.GetCacheLocationCurrentUseCase
import com.geosolution.geoapp.domain.use_case.SignInUseCase
import com.geosolution.geoapp.domain.use_case.SignUpUseCase
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
    fun providesCacheLocationCurrentUseCase(locationCurrentRepository: LocationCurrentRepository): CacheLocationCurrentUseCase {
        return CacheLocationCurrentUseCase(locationCurrentRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCacheLocationCurrentUseCase(locationCurrentRepository: LocationCurrentRepository): GetCacheLocationCurrentUseCase {
        return GetCacheLocationCurrentUseCase(locationCurrentRepository)
    }
}
