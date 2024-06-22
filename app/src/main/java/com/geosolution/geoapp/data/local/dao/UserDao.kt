package com.geosolution.geoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geosolution.geoapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun userGetStore(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun userSaveStore(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun userDeleteStore()
}