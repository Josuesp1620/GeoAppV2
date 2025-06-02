package com.geosolution.geoapp.di

import android.content.Context
import com.geosolution.geoapp.data.local.dao.ClientDao
import com.geosolution.geoapp.data.local.dao.DeviceDataDao
import com.geosolution.geoapp.data.local.dao.UserDao
import com.geosolution.geoapp.data.local.database.Database
import com.geosolution.geoapp.data.local.datastore.AuthDataStore
import com.geosolution.geoapp.data.local.datastore.LocationlDataStore
import com.geosolution.geoapp.data.remote.api.auth.AuthService
import com.geosolution.geoapp.data.repository.AuthRepositoryImpl
import com.geosolution.geoapp.data.repository.ClientRepositoryImpl
import com.geosolution.geoapp.data.repository.LocationRepositoryImpl
import com.geosolution.geoapp.data.repository.UserRepositoryImpl
import com.geosolution.geoapp.data.repository.DeviceDataRepositoryImpl
import com.geosolution.geoapp.domain.repository.AuthRepository
import com.geosolution.geoapp.domain.repository.ClientRepositoy
import com.geosolution.geoapp.domain.repository.DeviceDataRepository
import com.geosolution.geoapp.domain.repository.LocationRepository
import com.geosolution.geoapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
        authDataStore: AuthDataStore
    ): AuthRepository = AuthRepositoryImpl(
//      service = authService,
        dataStore = authDataStore
    )

    @Provides
    @Singleton
    fun provideLocationRepository(
        locationDataStore: LocationlDataStore
    ): LocationRepository = LocationRepositoryImpl(
        dataStore = locationDataStore
    )

    @Provides
    @Singleton
    fun provideClientRepository(
        dataBase: Database
    ): ClientRepositoy = ClientRepositoryImpl(
        dataBase = dataBase.clientDao
    )




    @Provides
    @Singleton
    fun provideUserRepository(
        @ApplicationContext context: Context,
        dataBase: Database
    ): UserRepository = UserRepositoryImpl(
        dataBase = dataBase.userDao
    )

    @Provides
    @Singleton
    fun provideDeviceDataRepository(
        deviceDataDao: DeviceDataDao
    ): DeviceDataRepository = DeviceDataRepositoryImpl(
        dataBase = deviceDataDao
    )
}