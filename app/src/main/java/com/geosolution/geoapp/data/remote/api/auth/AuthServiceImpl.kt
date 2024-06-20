package com.geosolution.geoapp.data.remote.api.auth

import com.geosolution.geoapp.core.constants.Constants.BASE_URL
import com.geosolution.geoapp.data.common.ktor
import com.geosolution.geoapp.data.remote.dto.ResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val client: HttpClient
) : AuthService {
    override suspend fun authSignIn(signInRequest: SignInRequest): ResponseDto<AuthDto?> {
        return ktor(client) {
            post("$BASE_URL/v1/auth/login") {
                contentType(ContentType.Application.Json)
                val body = mapOf(
                    "email" to signInRequest.email,
                    "password" to signInRequest.password
                )
                setBody(body)
            }.body()
        }
    }

    override suspend fun authSignUp(signUpRequest: SignUpRequest): ResponseDto<AuthDto?> {
        return ktor(client) {
            post("$BASE_URL/v1/auth/register") {
                contentType(ContentType.Application.Json)
                val body = mapOf(
                    "fullname" to signUpRequest.fullname,
                    "username" to signUpRequest.username,
                    "email" to signUpRequest.email,
                    "password" to signUpRequest.password
                )
                setBody(body)
            }.body()
        }
    }

}