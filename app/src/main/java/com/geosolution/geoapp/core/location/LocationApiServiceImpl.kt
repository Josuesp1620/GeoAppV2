package com.geosolution.geoapp.core.location

import android.content.Context
import android.util.Log
import com.geosolution.geoapp.domain.model.DeviceData
import com.geosolution.geoapp.domain.model.User
import com.geosolution.geoapp.domain.use_case.auth.AuthGetCacheUseCase
import com.geosolution.geoapp.domain.use_case.user.UserGetStoreUseCase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json


class LocationApiServiceImpl (
    private val context: Context,
    private val authGetCache: AuthGetCacheUseCase,
    private val userGetStoreUseCase: UserGetStoreUseCase

) {
    suspend fun getUserData(): User? {
        return try {
            authGetCache().firstOrNull()?.let { auth ->
                userGetStoreUseCase(auth.accessToken!!).firstOrNull()
            }
        } catch (e: Exception) {
            Log.d("LocationApiServiceImpl", "Error fetching user data: $e")
            null
        }
    }

    suspend fun sendLocationArray(devideData: List<DeviceData?>) {

        val client = HttpClient(CIO) {
            install(ContentNegotiation){
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val response: HttpResponse = client.post("http://200.121.128.47:3031/api/v1/data/save-array") {
            contentType(ContentType.Application.Json)
            setBody(devideData)
        }
        Log.d("HTTP_RESPONSE", "${response.body<Any>()}")
    }

    suspend fun sendLocation(devideData: DeviceData) {

        val client = HttpClient(CIO) {
            install(ContentNegotiation){
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val response: HttpResponse = client.post("http://200.121.128.47:3031/api/v1/data/save") {
            contentType(ContentType.Application.Json)
            setBody(devideData)
        }
        Log.d("HTTP_RESPONSE", "${response.body<Any>()}")
    }
}