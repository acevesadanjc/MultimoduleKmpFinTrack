/*
 * LoginMapper.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.network.mapper.login

import com.kmp.core.domain.model.login.request.LoginCredentialsModel
import com.kmp.core.network.dto.response.login.LoginResponse
import com.kmp.core.domain.model.login.response.LoginResultModel
import com.kmp.core.network.dto.request.login.LoginRequest


// DOMAIN LAYER -> DTO REQUEST DATA LAYER
// Modelo de la CAPA DOMAIN mapeado a DTO request de la capa CAPA DATA
fun LoginCredentialsModel.toRequest() = LoginRequest(
    email = email,
    password = password
)

// DTO RESPONSE DATA LAYER -> MODEL DOMAIN LAYER
// Response CAPA DATA mapeado a modelo de la CAPA DOMAIN
fun LoginResponse.toDomain() = LoginResultModel(
    accessToken = access_token.orEmpty(),
    expiresIn = expires_in ?: 0,
    refreshToken = refresh_token.orEmpty(),
    tokenType = token_type.orEmpty()
)