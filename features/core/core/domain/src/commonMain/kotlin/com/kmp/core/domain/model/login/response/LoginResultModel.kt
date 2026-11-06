package com.kmp.core.domain.model.login.response

data class LoginResultModel(
    val accessToken: String,
    val expiresIn: Int,
    val refreshToken: String,
    val tokenType: String
)