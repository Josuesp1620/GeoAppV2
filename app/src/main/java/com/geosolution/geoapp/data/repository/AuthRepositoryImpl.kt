package com.geosolution.geoapp.data.repository

import android.util.Log
import com.geosolution.geoapp.core.constants.Status.SUCCESS
import com.geosolution.geoapp.core.constants.Status.SUCCESS_CREATE
import com.geosolution.geoapp.core.utils.Action
import com.geosolution.geoapp.data.local.AuthLocalDataStore
import com.geosolution.geoapp.data.local.entity.asDatabaseEntity
import com.geosolution.geoapp.data.remote.api.auth.AuthService
import com.geosolution.geoapp.data.remote.api.auth.SignInRequest
import com.geosolution.geoapp.data.remote.api.auth.SignUpRequest
import com.geosolution.geoapp.domain.model.Auth
import com.geosolution.geoapp.domain.repository.AuthRepository
import com.geosolution.geoapp.presentation.ui.utils.toAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val authLocalDataStore: AuthLocalDataStore
) : AuthRepository {

    override fun authSignIn(signInRequest: SignInRequest): Flow<Action<Auth>> = flow  {
        emit(Action.Loading())
        val response = authService.authSignIn(signInRequest)

        Log.d("AuthRepositoryImpl", response.data.toString())

        when(response.code) {
            SUCCESS -> emit(Action.Success(data = response.data?.asDomain()))
        }
    }.catch {
        emit(it.toAction())
    }

    override fun authSignUp(signUpRequest: SignUpRequest): Flow<Action<Auth>> = flow{
        emit(Action.Loading())
        val response = authService.authSignUp(signUpRequest)

        when(response.code) {
            SUCCESS_CREATE -> emit(Action.Success(data = response.data?.asDomain()))
        }
    }.catch {
        emit(it.toAction())
    }

    override suspend fun saveAuthCache(auth: Auth) = authLocalDataStore.save(auth.asDatabaseEntity())

    override suspend fun getAuthCache() : Flow<Auth?> = authLocalDataStore.getAuth().map { it?.asDomain() }
}