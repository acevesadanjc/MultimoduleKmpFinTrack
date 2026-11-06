/*
 * LoginValidationResult.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.feature.login.featureLogic.usecase

import org.jetbrains.compose.resources.StringResource


data class LoginValidationResult(
    val passwordError: StringResource? = null
) {

    val isValid: Boolean
        get() = passwordError == null

    val hasError: Boolean
        get() = !isValid

    val message = passwordError
}