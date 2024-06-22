package com.geosolution.geoapp.domain.use_case.auth

import com.geosolution.geoapp.core.utils.Action
import com.geosolution.geoapp.data.remote.api.auth.SignInRequest
import com.geosolution.geoapp.domain.model.Auth
import com.geosolution.geoapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(request: SignInRequest): Flow<Action<Auth>> = authRepository.authSignIn(request)

}
