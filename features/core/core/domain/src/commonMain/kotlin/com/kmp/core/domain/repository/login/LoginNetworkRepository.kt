/*
 * LoginNetworkRepository.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.domain.repository.login

import com.kmp.core.domain.model.login.request.LoginCredentialsModel
import com.kmp.core.domain.model.login.response.LoginResultModel
import com.kmp.core.tools.network.api.NetworkResult
import kotlinx.coroutines.flow.Flow

interface LoginNetworkRepository {

    fun login(
        url: String,
        params: LoginCredentialsModel
    ): Flow<NetworkResult<LoginResultModel>>

}
