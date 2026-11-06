/*
 * SplashNavigation.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.feature.splash.featureUi.presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kmp.core.navigation.deeplink.DeepLinkCoordinator
import com.kmp.core.navigation.router.NavigationFeaturesRouter
import com.kmp.feature.splash.featureUi.presentation.ui.SplashScreen
import org.koin.compose.koinInject

data object SplashScreenInstance : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val router: NavigationFeaturesRouter = koinInject()
        SplashScreen(
            onNavigateToNextScreen = {
                navigator.replaceAll(router.getLoginScreen())
            },
            onNavigateToWelcome = {
                navigator.replaceAll(router.getLoginScreen())
            }
        )
    }
}
