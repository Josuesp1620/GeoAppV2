package com.geosolution.geoapp.domain.repository

import com.geosolution.geoapp.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepositoy {

    suspend fun clientCreate(client: Client)

    fun clientGetById(id: String): Flow<Client>

    fun clientGetAll(): Flow<List<Client>>


}