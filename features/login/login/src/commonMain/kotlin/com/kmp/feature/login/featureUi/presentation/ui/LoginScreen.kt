/*
 * LoginScreen.kt.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.feature.login.featureUi.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kmp.core.design.adaptive.AdaptiveScreenContent
import com.kmp.core.design.component.container.SafeScreenContainer
import com.kmp.core.design.component.container.SafeScreenContainerTest
import com.kmp.core.design.component.dialog.ErrorDialog
import com.kmp.core.design.component.loader.Loader
import com.kmp.core.design.model.dialog.DialogUiModel
import com.kmp.core.design.theme.AppTheme
import com.kmp.feature.login.featureUi.presentation.ui.phone.LoginPhoneContainer
import com.kmp.feature.login.featureUi.presentation.ui.tablet.LoginTabletContainer
import com.kmp.feature.login.featureUi.presentation.viewmodel.LoginNavigationTargetUiState
import com.kmp.feature.login.featureUi.presentation.viewmodel.LoginUiState
import com.kmp.feature.login.featureUi.presentation.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateToRegister: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
) {
    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()

    LaunchedEffect(loginUiState.navigationTarget) {
        loginUiState.navigationTarget?.let { target ->
            when (target) {
                is LoginNavigationTargetUiState.Home -> onNavigateToHome()
                is LoginNavigationTargetUiState.Register -> onNavigateToRegister()
            }
            viewModel.navigationHandled()
        }
    }

    SafeScreenContainer {
        AdaptiveScreenContent(
            phoneContainer = { LoginPhoneContainer(
                state = loginUiState,
                onEmailChange = viewModel::updateEmail,
                onPasswordChange = viewModel::updatePassword,
                onLoginClick = viewModel::validateFields,
                onRegisterClick = viewModel::navigateToRegister
            ) },
            tabletContainer = { LoginTabletContainer(
                state = loginUiState,
                onEmailChange = viewModel::updateEmail,
                onPasswordChange = viewModel::updatePassword,
                onLoginClick = viewModel::validateFields,
                onRegisterClick = viewModel::navigateToRegister
            ) },
        )
    }

    Loader(
        backgroundColor = AppTheme.colors.background,
        currentState = loginUiState.isLoading
    )

    loginUiState.errorDialog?.let {
        ErrorDialog(
            dialogUiModel = DialogUiModel(
                title = it.title,
                message = it.message,
                primaryButtonText = it.primaryButtonText
            ),
            titleTextColor = AppTheme.colors.text.black,
            descriptionTextColor = AppTheme.colors.text.black,
            primaryButtonBackgroundColor = AppTheme.colors.primary,
            primaryButtonTextColor = AppTheme.colors.text.black,
            onPrimaryButtonClick = viewModel::dismissDialog
        )
    }
}

@Composable
private fun LoginAdaptivePreviewContent() {
    SafeScreenContainerTest {
        AdaptiveScreenContent(
            phoneContainer = { LoginPhoneContainer(
                state = LoginUiState()
            ) },
            tabletContainer = { LoginTabletContainer(
                state = LoginUiState()
            ) },
        )
    }
}

@Preview(
    name = "Phone - Portrait",
    device = "spec:width=1080px,height=2340px,dpi=440",
    showSystemUi = true,
)
@Composable
private fun LoginPortraitPreview() {
    LoginAdaptivePreviewContent()
}

@Preview(
    name = "Phone - Landscape",
    device = "spec:width=2340px,height=1080px,dpi=440",
    showSystemUi = true,
)
@Composable
private fun LoginLandscapePreview() {
    LoginAdaptivePreviewContent()
}

@Preview(
    name = "Tablet 7 inch",
    device = "spec:width=1920px,height=1200px,dpi=320",
    showSystemUi = true,
)
@Composable
private fun LoginTabletAdaptivePreview() {
    LoginAdaptivePreviewContent()
}