/*
 * LoginRequest.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.network.dto.request.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)