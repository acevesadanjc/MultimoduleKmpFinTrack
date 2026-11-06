/*
 * LoginValidationUseCase.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.feature.login.featureLogic.usecase

import com.kmp.feature.login.generated.resources.Res
import com.kmp.feature.login.generated.resources.login_field_message
import org.jetbrains.compose.resources.StringResource
import org.koin.core.annotation.Factory

@Factory
class LoginValidationUseCase {

    operator fun invoke(
        password: String
    ): LoginValidationResult {
        var passwordError: StringResource? = null

        if (password.isEmpty()) {
            passwordError = Res.string.login_field_message
        }

        return LoginValidationResult(passwordError = passwordError)
    }
}