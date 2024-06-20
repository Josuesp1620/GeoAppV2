package com.geosolution.geoapp.domain.use_case

import com.geosolution.geoapp.data.remote.api.auth.SignUpRequest
import com.geosolution.geoapp.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        signUpRequest: SignUpRequest
    )= authRepository.authSignUp(signUpRequest)
}