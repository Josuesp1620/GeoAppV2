package com.geosolution.geoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geosolution.geoapp.data.local.entity.ClientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Query("SELECT * FROM client")
    fun clientGetAllStore(): Flow<List<ClientEntity>>

    @Query("SELECT * FROM client WHERE id = :id")
    fun clientGetByIdStore(id: String): Flow<ClientEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun clientSaveStore(client: ClientEntity)
}
