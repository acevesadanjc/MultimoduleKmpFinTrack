/*
 * MainActivity.kt
 * Copyright (c) 2026. All rights reserved
*/
package com.kmp.host

import android.content.Intent
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kmp.core.navigation.deeplink.DeepLinkCoordinator
import com.kmp.feature.splash.featureUi.presentation.controller.NavigationController
import io.kmpbits.splash.SplashActivity

class MainActivity : SplashActivity() {

    override fun onPreCreate() {
        enableEdgeToEdge()
        validateIfHaveDeepLink()
    }

    private fun validateIfHaveDeepLink() {
        intent?.data?.let { uri ->
            DeepLinkCoordinator.saveDeepLinkRoute(uri.toString())
        }
    }

    override suspend fun isReady(): Boolean = true

    override fun onFinished() {
        setContent {
            NavigationController()
        }
    }

    /**
     * Este metodo se llama cuando la actividad recibe un nuevo intent mientras ya está en ejecución.
     * Esto es útil para manejar enlaces profundos (deep links) que pueden abrir la aplicación mientras ya está abierta.
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent.dataString?.let { url ->
            DeepLinkCoordinator.saveDeepLinkRoute(url)
        }
    }
}
