/*
 * NetworkClient.ios.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.network.config.client

import com.kmp.core.datastore.config.AppDataStore
import com.kmp.core.tools.logs.Log
import com.kmp.core.tools.network.constants.Network
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

actual fun createHttpClient(appDataStore: AppDataStore): HttpClient = HttpClient(engineFactory = Darwin) {

    defaultRequest {
        url(urlString = Network.Client.BASE_URL)
    }

    install(plugin = HttpTimeout) {
        requestTimeoutMillis = Network.Constants.REQUEST_TIMEOUT_MILLIS
        connectTimeoutMillis = Network.Constants.CONNECT_TIMEOUT_MILLIS
        socketTimeoutMillis  = Network.Constants.SOCKET_TIMEOUT_MILLIS
    }

    install(plugin = Auth) {
        bearer {
            loadTokens {
                val token = appDataStore.userToken.first()
                if (!token.isNullOrBlank()) {
                    BearerTokens(
                        accessToken = token,
                        refreshToken = "",
                    )
                } else {
                    null
                }
            }
        }
    }

    install(plugin = Logging) {
        level = LogLevel.BODY
        logger = object : Logger {
            override fun log(message: String) {
                /*if (BuildKonfig.IS_DEBUG) {}*/
                Log.network(
                    message = message,
                    tag = Network.Constants.TAG_IOS
                )
            }
        }
    }

    install(plugin = ContentNegotiation) {
        json(
            json = Json { ignoreUnknownKeys = true },
            contentType = ContentType.Application.Json,
        )
    }
}
