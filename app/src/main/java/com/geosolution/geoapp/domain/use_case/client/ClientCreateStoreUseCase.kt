package com.geosolution.geoapp.domain.use_case.client

import com.geosolution.geoapp.domain.model.Client
import com.geosolution.geoapp.domain.repository.ClientRepositoy
import javax.inject.Inject

class ClientCreateStoreUseCase @Inject constructor(
    private val repository: ClientRepositoy
) {
    suspend operator fun invoke(client: Client) = repository.clientSaveStore(client)
}