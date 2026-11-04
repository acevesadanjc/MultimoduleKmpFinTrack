package com.kmp.host.navigation

import cafe.adriel.voyager.core.screen.Screen
import com.kmp.core.navigation.router.NavigationFeaturesRouter
import com.kmp.feature.splash.featureUi.presentation.navigation.SplashScreenInstance
import com.kmp.feature.login.featureUi.presentation.navigation.LoginScreenInstance

class NavigationFeaturesRouterImpl : NavigationFeaturesRouter {
    // Define función para flujo por flavor.
    //override fun getScreenAfterSplash(): Screen = WelcomeScreenInstance
    // Define función para flujos ya establecidos.
    override fun getSplashScreen(): Screen = SplashScreenInstance
    override fun getLoginScreen(): Screen = LoginScreenInstance
}