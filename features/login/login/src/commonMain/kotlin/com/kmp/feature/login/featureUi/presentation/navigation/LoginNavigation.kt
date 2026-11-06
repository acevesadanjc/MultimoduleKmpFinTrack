package com.kmp.feature.login.featureUi.presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kmp.core.navigation.router.NavigationFeaturesRouter
import com.kmp.core.tools.logs.Log
import com.kmp.feature.login.featureUi.presentation.ui.LoginScreen
import org.koin.compose.koinInject

data object LoginScreenInstance : Screen {
    @Composable
    override fun Content() {
        //Navigator: Permite navegar en pantallas dentro del feature
        val navigator = LocalNavigator.currentOrThrow
        // Router: Permite navegar entre features
        val router: NavigationFeaturesRouter = koinInject()

        LoginScreen(
            onNavigateToRegister = {
                Log.info(message = "onNavigateToRegister", tag = "LoginNavigation")
                //navigator.push(RegisterScreenInstance)
            },
            onNavigateToHome = {
                Log.info(message = "onNavigateToHome", tag = "LoginNavigation")
                //navigator.push(HomeScreenInstance)
            }
        )
    }
}