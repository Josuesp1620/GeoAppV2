package com.geosolution.geoapp.data.remote.api.auth

import com.geosolution.geoapp.data.remote.dto.ResponseDto

interface AuthService {
    suspend fun authSignIn(signInRequest: SignInRequest) : ResponseDto<AuthDto?>
    suspend fun authSignUp(signUpRequest: SignUpRequest) : ResponseDto<AuthDto?>
}