package com.geosolution.geoapp.di

import com.geosolution.geoapp.domain.repository.AuthRepository
import com.geosolution.geoapp.domain.repository.ClientRepositoy
import com.geosolution.geoapp.domain.repository.LocationRepository
import com.geosolution.geoapp.domain.repository.UserRepository
import com.geosolution.geoapp.domain.use_case.auth.AuthGetCacheUseCase
import com.geosolution.geoapp.domain.use_case.auth.AuthSaveCacheUseCase
import com.geosolution.geoapp.domain.use_case.auth.AuthSignInUseCase
import com.geosolution.geoapp.domain.use_case.auth.AuthSignUpUseCase
import com.geosolution.geoapp.domain.use_case.client.ClientCreateStoreUseCase
import com.geosolution.geoapp.domain.use_case.client.ClientGetAllStoreUseCase
import com.geosolution.geoapp.domain.use_case.client.ClientGetByIdStoreUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationDeleteCacheUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationGetCacheUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationSaveCacheUseCase
import com.geosolution.geoapp.domain.use_case.user.UserGetStoreUseCase
import com.geosolution.geoapp.domain.use_case.user.UserSaveStoreUseCase
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
    fun provideAuthGetCacheUseCase(authRepository: AuthRepository): AuthGetCacheUseCase {
        return AuthGetCacheUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAuthSaveCacheUseCase(authRepository: AuthRepository): AuthSaveCacheUseCase {
        return AuthSaveCacheUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAuthSignInUseCase(authRepository: AuthRepository): AuthSignInUseCase {
        return AuthSignInUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAuthSignUpUseCase(authRepository: AuthRepository): AuthSignUpUseCase {
        return AuthSignUpUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideClientCreateStoreUseCase(clientRepository: ClientRepositoy): ClientCreateStoreUseCase {
        return ClientCreateStoreUseCase(clientRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideClientGetByIdStoreUseCase(clientRepository: ClientRepositoy): ClientGetByIdStoreUseCase {
        return ClientGetByIdStoreUseCase(clientRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideClientGetAllStoreUseCase(clientRepository: ClientRepositoy): ClientGetAllStoreUseCase {
        return ClientGetAllStoreUseCase(clientRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideLocationGetCacheUseCase(locationRepository: LocationRepository): LocationGetCacheUseCase {
        return LocationGetCacheUseCase(locationRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideLocationSaveCacheUseCase(locationRepository: LocationRepository): LocationSaveCacheUseCase {
        return LocationSaveCacheUseCase(locationRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideLocationDeleteCacheUseCase(locationRepository: LocationRepository): LocationDeleteCacheUseCase {
        return LocationDeleteCacheUseCase(locationRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUserGetStoreUseCase(userRepository: UserRepository): UserGetStoreUseCase {
        return UserGetStoreUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUserSaveStoreUseCase(userRepository: UserRepository): UserSaveStoreUseCase {
        return UserSaveStoreUseCase(userRepository)
    }

}
