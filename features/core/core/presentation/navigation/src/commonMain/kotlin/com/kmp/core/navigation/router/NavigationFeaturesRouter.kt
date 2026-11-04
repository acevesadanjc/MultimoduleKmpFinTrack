package com.kmp.core.navigation.router

import cafe.adriel.voyager.core.screen.Screen
import com.kmp.core.navigation.enum.DeepLinkDestination

interface NavigationFeaturesRouter {
    // Define función para flujo por deep link.
    /*fun getDeepLinkScreen(destination: DeepLinkDestination?): Screen =
        when(destination){
            //DeepLinkDestination.Login -> getLoginScreen()
            else -> getScreenAfterSplash()
        }*/
    // Define función para flujo por flavor.
    //fun getScreenAfterSplash(): Screen
    // Define función para flujos ya establecidos.
    fun getSplashScreen(): Screen
    fun getLoginScreen(): Screen
}