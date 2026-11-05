/*
 * SplashUiEvent.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.feature.splash.featureUi.presentation.viewmodel

sealed class SplashUiEvent {
    internal data object Idle : SplashUiEvent()
    data object NavigateToWelcome: SplashUiEvent()
    data object NavigateToNextScreen: SplashUiEvent()
}
