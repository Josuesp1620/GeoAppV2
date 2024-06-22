package com.geosolution.geoapp.domain.use_case.auth

import com.geosolution.geoapp.domain.model.Auth
import com.geosolution.geoapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthGetCacheUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Auth?> = authRepository.authGetCache()

}