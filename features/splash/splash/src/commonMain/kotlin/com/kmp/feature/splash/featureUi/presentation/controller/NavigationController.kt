/*
 * NavigationController.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.feature.splash.featureUi.presentation.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScaleTransition
import com.kmp.core.design.theme.AppTheme
import com.kmp.core.navigation.deeplink.DeepLinkCoordinator
import com.kmp.core.navigation.router.NavigationFeaturesRouter
import com.kmp.feature.splash.featureUi.presentation.navigation.SplashScreenInstance
import org.koin.compose.koinInject

@Composable
fun NavigationController(){
    //val router: NavigationFeaturesRouter = koinInject()
    AppTheme {
        Navigator(screen = SplashScreenInstance){ navigator ->
            ScaleTransition(navigator)
            // Si hay un deep link pendiente, reemplaza la pantalla actual con la pantalla correspondiente al deep link.
            /*LaunchedEffect(navigator){
                DeepLinkCoordinator.pending.collect { destination ->
                    if(destination != null && navigator.lastItem != SplashScreenInstance){
                        val pending = DeepLinkCoordinator.getDeepLinkRoute()
                        if(pending != null){
                            //navigator.replaceAll(router.getDeepLinkScreen(pending))
                        }
                    }
                }
            }*/
        }
    }
}
