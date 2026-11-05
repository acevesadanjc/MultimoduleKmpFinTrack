/*
 * SplashScreen.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.feature.splash.featureUi.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kmp.core.design.component.container.SafeScreenContainer
import com.kmp.core.design.component.container.SafeScreenContainerTest
import com.kmp.core.design.theme.AppTheme
import com.kmp.core.tools.compose.flow.CollectWithLifecycleLaunchEffect
import com.kmp.core.tools.constants.ConstantsGeneral
import com.kmp.core.tools.logs.Log
import com.kmp.feature.splash.featureUi.presentation.viewmodel.SplashUiEvent
import com.kmp.feature.splash.featureUi.presentation.viewmodel.SplashViewModel
import com.kmp.feature.splash.generated.resources.Res
import com.kmp.feature.splash.generated.resources.idle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    onNavigateToWelcome: () -> Unit = {},
    onNavigateToNextScreen: () -> Unit = {},
) {

    val splashUiState by viewModel.splashUiState.collectAsStateWithLifecycle()

    viewModel.splashUiEvent.CollectWithLifecycleLaunchEffect{ uiEvent ->
        when (uiEvent) {
            SplashUiEvent.Idle -> {
                Log.info(message = ConstantsGeneral.IDLE)
            }
            SplashUiEvent.NavigateToNextScreen -> {
                onNavigateToNextScreen()
            }
            SplashUiEvent.NavigateToWelcome -> {
                onNavigateToWelcome()
            }
        }
    }

    SafeScreenContainer(
        systemColor = AppTheme.colors.backgrounds.blue
    ) {
        SplashContainer(
            isLogoVisible = splashUiState.isLogoVisible,
            isBrandingVisible = splashUiState.isBrandingVisible,
            onLogoVisibleChange = { isVisible -> viewModel.updateIsLogoVisible(isVisible) },
            onBrandingVisibleChange = { isVisible -> viewModel.updateIsBrandingVisible(isVisible) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SafeScreenContainerTest {
        SplashContainer()
    }
}
