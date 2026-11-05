package com.kmp.host.navigation

import cafe.adriel.voyager.core.screen.Screen
import com.kmp.core.navigation.router.NavigationFeaturesRouter
import com.kmp.feature.splash.featureUi.presentation.navigation.SplashScreenInstance

class NavigationFeaturesRouterImpl : NavigationFeaturesRouter {
    override fun getSplashScreen(): Screen = SplashScreenInstance
}