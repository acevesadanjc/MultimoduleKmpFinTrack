/*
 * LoginNetworkDataSourceImpl.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.network.datasource.login

import com.kmp.core.domain.model.login.request.LoginCredentialsModel
import com.kmp.core.domain.model.login.response.LoginResultModel
import com.kmp.core.network.dto.response.login.LoginResponse
import com.kmp.core.network.mapper.login.toDomain
import com.kmp.core.network.mapper.login.toRequest
import com.kmp.core.tools.network.api.NetworkResult
import com.kmp.core.tools.network.api.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.annotation.Factory

@Factory
class LoginNetworkDataSourceImpl(
    private val httpClient: HttpClient,
) : LoginNetworkDataSource {

    override suspend fun login(
        url: String,
        params: LoginCredentialsModel,
    ): NetworkResult<LoginResultModel> =
        safeApiCall(
            apiCall = {
                httpClient.post(urlString = url) {
                    contentType(type = ContentType.Application.Json)
                    setBody(body = params.toRequest())
                }
            },
            transform = { data: LoginResponse ->
                data.toDomain()
            },
        )
}