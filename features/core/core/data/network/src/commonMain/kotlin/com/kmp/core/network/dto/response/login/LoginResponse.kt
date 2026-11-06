/*
 * LoginResponse.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.network.dto.response.login

import com.kmp.core.tools.network.api.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val access_token: String?,
    val expires_in: Int?,
    val refresh_token: String?,
    val token_type: String?
): BaseResponse()