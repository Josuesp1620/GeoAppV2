package com.geosolution.geoapp.domain.use_case.client

import com.geosolution.geoapp.domain.model.Client
import com.geosolution.geoapp.domain.repository.ClientRepositoy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClientGetAllStoreUseCase @Inject constructor(
    private val repository: ClientRepositoy
) {
    operator fun invoke(): Flow<List<Client>> = repository.clientGetAllStore()
}