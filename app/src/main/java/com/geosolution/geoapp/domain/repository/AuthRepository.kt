package com.geosolution.geoapp.domain.repository

import com.geosolution.geoapp.core.utils.Action
import com.geosolution.geoapp.data.remote.api.auth.SignInRequest
import com.geosolution.geoapp.data.remote.api.auth.SignUpRequest
import com.geosolution.geoapp.domain.model.Auth
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun authSignIn(signInRequest: SignInRequest): Flow<Action<Auth>>

    fun authSignUp(signUpRequest: SignUpRequest): Flow<Action<Auth>>

    suspend fun saveAuthCache(auth: Auth)

    suspend fun getAuthCache() : Flow<Auth?>
}
