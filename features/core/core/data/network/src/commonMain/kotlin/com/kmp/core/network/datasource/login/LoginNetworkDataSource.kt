/*
 * LoginNetworkDataSource.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.network.datasource.login

import com.kmp.core.domain.model.login.request.LoginCredentialsModel
import com.kmp.core.domain.model.login.response.LoginResultModel
import com.kmp.core.tools.network.api.NetworkResult

interface LoginNetworkDataSource {

    suspend fun login(
        url: String,
        params: LoginCredentialsModel,
    ): NetworkResult<LoginResultModel>
}
