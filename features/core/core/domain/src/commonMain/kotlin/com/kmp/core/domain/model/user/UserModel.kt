/*
 * UserModel.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.domain.model.user

data class UserModel(
    val id: Int = 0,
    val email: String,
    val password: String,
)
