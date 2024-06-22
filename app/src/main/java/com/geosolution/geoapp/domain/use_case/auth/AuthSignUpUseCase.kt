package com.geosolution.geoapp.domain.use_case.auth

import com.geosolution.geoapp.core.utils.Action
import com.geosolution.geoapp.data.remote.api.auth.SignUpRequest
import com.geosolution.geoapp.domain.model.Auth
import com.geosolution.geoapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthSignUpUseCase@Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(request: SignUpRequest): Flow<Action<Auth>> = authRepository.authSignUp(request)

}