package com.geosolution.geoapp.domain.use_case.client

import com.geosolution.geoapp.domain.model.Client
import com.geosolution.geoapp.domain.repository.ClientRepositoy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClientGetByIdStoreUseCase @Inject constructor(
    private val repository: ClientRepositoy
) {
    operator fun invoke(id: String): Flow<Client> = repository.clientGetByIdStore(id)
}