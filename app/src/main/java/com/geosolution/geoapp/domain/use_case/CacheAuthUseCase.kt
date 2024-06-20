package com.geosolution.geoapp.domain.use_case

import com.geosolution.geoapp.domain.model.Auth
import com.geosolution.geoapp.domain.repository.AuthRepository
import javax.inject.Inject

class CacheAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(auth: Auth) = authRepository.saveAuthCache(auth)
}
