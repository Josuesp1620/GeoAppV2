package com.geosolution.geoapp.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.geosolution.geoapp.data.local.dao.ClientDao
import com.geosolution.geoapp.data.local.datastore.AuthDataStore
import com.geosolution.geoapp.data.local.dao.UserDao
import com.geosolution.geoapp.data.local.database.Database
import com.geosolution.geoapp.data.local.datastore.LocationlDataStore
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
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): Database = Room.databaseBuilder(
        context = applicationContext,
        klass = Database::class.java,
        name = "GeoAppDataBase"
    ).build()

    @Provides
    @Singleton
    fun provideUserDao(database: Database) : UserDao {
        return database.userDao
    }
    @Provides
    @Singleton
    fun provideClientDao(database: Database) : ClientDao {
        return database.clientDao
    }

}