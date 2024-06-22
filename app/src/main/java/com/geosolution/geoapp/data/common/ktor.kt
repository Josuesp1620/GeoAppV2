package com.geosolution.geoapp.data.common

import com.geosolution.geoapp.data.remote.dto.ResponseErrorDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


suspend inline fun <T> ktor(client: HttpClient, block: HttpClient.() -> T): T {
    return try {
//        client.tryClearToken()
        client.block()
    } catch (e: ClientRequestException) {
        val error = e.response.body<ResponseErrorDto>()
        error(error.message)
    } catch (e: ServerResponseException) {
        val error = e.response.body<ResponseErrorDto>()
        error(error.message)
    }
}

fun HttpClient.tryClearToken() {
    plugin(Auth).providers
        .filterIsInstance<BearerAuthProvider>()
        .firstOrNull()
        ?.clearToken()
}

fun HttpClient(/*authLocalDataSource: AuthLocalDataSource*/) = HttpClient(CIO) {
    expectSuccess = true
    install(WebSockets)
    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.ALL
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                encodeDefaults = true
            }
        )
    }
//    install(Auth) {
//        bearer {
//            loadTokens {
//                val token = authLocalDataSource.getAuth().firstOrNull()
//                val (accessToken, _, _) = token ?: return@loadTokens null
//                BearerTokens(accessToken, accessToken)
//            }
//        }
//    }
}