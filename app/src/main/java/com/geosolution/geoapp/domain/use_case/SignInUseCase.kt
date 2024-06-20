package com.geosolution.geoapp.domain.use_case

import com.geosolution.geoapp.data.remote.api.auth.SignInRequest
import com.geosolution.geoapp.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(
        signInRequest: SignInRequest
    ) = authRepository.authSignIn(signInRequest)

}