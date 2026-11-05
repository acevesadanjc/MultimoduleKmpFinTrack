/*
 * MainViewController.kt
 * Copyright (c) 2026. All rights reserved
*/
package com.kmp.host

import androidx.compose.ui.window.ComposeUIViewController
import com.kmp.feature.splash.featureUi.presentation.controller.NavigationController
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.kmpbits.splash.SplashConfig

fun MainViewController() = ComposeUIViewController(
    configure = {
        // Logs KMP
        Napier.base(antilog = DebugAntilog())
    },
) {
    SplashConfig(isReady = { true }) {
        NavigationController()
    }
}
