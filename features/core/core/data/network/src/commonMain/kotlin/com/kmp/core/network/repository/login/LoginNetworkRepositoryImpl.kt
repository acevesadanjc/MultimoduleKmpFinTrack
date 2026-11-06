/*
 * LoginRepositoryImpl.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.network.repository.login

import com.kmp.core.domain.model.login.request.LoginCredentialsModel
import com.kmp.core.domain.model.login.response.LoginResultModel
import com.kmp.core.domain.repository.login.LoginNetworkRepository
import com.kmp.core.network.datasource.login.LoginNetworkDataSource
import com.kmp.core.tools.network.api.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Factory

@Factory
class LoginNetworkRepositoryImpl(
    private val networkDataSource: LoginNetworkDataSource,
    private val dispatcher: CoroutineDispatcher,
) : LoginNetworkRepository {

    override fun login(
        url: String,
        params: LoginCredentialsModel,
    ): Flow<NetworkResult<LoginResultModel>> = flow {
        emit(
            networkDataSource.login(
                url = url,
                params = params
            )
        )
    }.flowOn(context = dispatcher)
}