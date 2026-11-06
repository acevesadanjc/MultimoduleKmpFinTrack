/*
 * BaseResponse.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.tools.network.api

import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse(
    val success: Boolean? = false,
    val message: String? = "",
    val error: ErrorPayload? = null
) {
    /**
     * Retorna true si la respuesta no trae objeto 'error'.
     */
    val isSuccessful: Boolean
        get() = error == null

}