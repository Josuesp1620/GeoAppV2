package com.geosolution.geoapp.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.geosolution.geoapp.data.local.datastore.AuthLocalDataStore
import com.geosolution.geoapp.data.local.dao.UserDao
import com.geosolution.geoapp.data.local.database.Database
import com.geosolution.geoapp.data.local.datastore.LocationCurrentLocalDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAuthLocalDataStore(@ApplicationContext context: Context): AuthLocalDataStore {
        return AuthLocalDataStore(dataStore = context.authStore)
    }

    @Provides
    @Singleton
    fun provideLocationCurrentLocalDataStore(@ApplicationContext context: Context): LocationCurrentLocalDataStore {
        return LocationCurrentLocalDataStore(dataStore = context.locationCurrentStore)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): Database = Room.databaseBuilder(
        context = applicationContext,
        klass = Database::class.java,
        name = "uniHubSocialDataBase"
    ).build()

    @Provides
    @Singleton
    fun provideUserDao(database: Database) : UserDao {
        return database.userDao
    }
}


private val Context.authStore by preferencesDataStore("auth")

private val Context.locationCurrentStore by preferencesDataStore("location_current")

