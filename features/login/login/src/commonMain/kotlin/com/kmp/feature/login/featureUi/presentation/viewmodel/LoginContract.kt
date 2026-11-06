/*
 * LoginContract.kt.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.feature.login.featureUi.presentation.viewmodel

import com.kmp.core.design.enums.StatusLoading
import com.kmp.core.design.model.dialog.DialogUiModel

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: StatusLoading = StatusLoading.DISMISS_LOADING,
    val errorDialog: DialogUiModel? = null,
    val navigationTarget: LoginNavigationTargetUiState? = null
)

sealed interface LoginNavigationTargetUiState {
    data object Home : LoginNavigationTargetUiState
    data object Register : LoginNavigationTargetUiState
}