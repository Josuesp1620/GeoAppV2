package com.geosolution.geoapp.domain.repository

import com.geosolution.geoapp.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepositoy {

    suspend fun clientSaveStore(client: Client)

    fun clientGetByIdStore(id: String): Flow<Client>

    fun clientGetAllStore(): Flow<List<Client>>


}