/*
 * SplashViewModel.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.feature.splash.featureUi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel
import kotlin.time.Duration.Companion.milliseconds

@KoinViewModel
class SplashViewModel: ViewModel() {

    init {
        simulateSplash()
    }

    private var _splashUiEvent = MutableStateFlow<SplashUiEvent>(SplashUiEvent.Idle)
    val splashUiEvent: StateFlow<SplashUiEvent> = _splashUiEvent.asStateFlow()

    private var _splashUiState = MutableStateFlow(SplashUiState())
    val splashUiState: StateFlow<SplashUiState> = _splashUiState.asStateFlow()

    fun simulateSplash() = viewModelScope.launch{
        delay(duration = 3000.milliseconds)
        //_splashUiEvent.emit(SplashUiEvent.NavigateToNextScreen)
        _splashUiEvent.emit(SplashUiEvent.NavigateToWelcome)
    }

    fun updateIsLogoVisible(isLogoVisible: Boolean) = viewModelScope.launch{
        _splashUiState.update { state -> state.copy(isLogoVisible = isLogoVisible)}
    }

    fun updateIsBrandingVisible(isBrandingVisible: Boolean) = viewModelScope.launch{
        _splashUiState.update { state -> state.copy(isBrandingVisible = isBrandingVisible)}
    }

    fun resetUiEvent() = viewModelScope.launch {
        _splashUiEvent.emit(SplashUiEvent.Idle)
    }
}
