/*
 * LoginNetworkUseCase.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.domain.usecase.login

import com.kmp.core.domain.model.login.request.LoginCredentialsModel
import com.kmp.core.domain.model.login.response.LoginResultModel
import com.kmp.core.domain.repository.login.LoginNetworkRepository
import com.kmp.core.tools.network.api.NetworkResult
import com.kmp.core.tools.network.enums.CodeServices
import kotlinx.coroutines.flow.Flow

class LoginNetworkUseCase(
    private val repository: LoginNetworkRepository,
) {
    // suspend
    fun login(
        email: String,
        password: String,
        url: String,
    ): Flow<NetworkResult<LoginResultModel>> =
        repository.login(
            url = url,
            params = LoginCredentialsModel(
                email = email,
                password = password
            )
        )

    /*
    fun validateServiceLogin(
        loginDataModel: LoginResultModel,
        onSuccessServices: () -> Unit = {},
        onErrorServices: () -> Unit = {}
    ) {
        when (loginDataModel.codeServices) {
            CodeServices.RESPONSE_OK -> {
                loginDataModel.data?.let {
                    onSuccessServices()
                } ?: run {
                    onErrorServices()
                }
            }

            CodeServices.USER_NOT_REGISTER -> {
                onErrorServices()
            }

            CodeServices.UNKNOWN_CODE -> {
                onErrorServices()
            }
        }
    }
    */
}