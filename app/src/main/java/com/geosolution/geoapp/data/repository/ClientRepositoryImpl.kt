package com.geosolution.geoapp.data.repository

import com.geosolution.geoapp.data.local.dao.ClientDao
import com.geosolution.geoapp.data.local.entity.asDatabaseEntity
import com.geosolution.geoapp.domain.model.Client
import com.geosolution.geoapp.domain.repository.ClientRepositoy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val dataBase: ClientDao
): ClientRepositoy {
    override suspend fun clientSaveStore(client: Client) {
        client.id = 0
        dataBase.clientSaveStore(client.asDatabaseEntity())
    }

    override fun clientGetByIdStore(id: Int): Flow<Client> = dataBase.clientGetByIdStore(id).map { it?.asDomain()!! }

    override fun clientGetAllStore(): Flow<List<Client>> =
        dataBase.clientGetAllStore()
            .map { list -> list.map { it.asDomain() } }

}