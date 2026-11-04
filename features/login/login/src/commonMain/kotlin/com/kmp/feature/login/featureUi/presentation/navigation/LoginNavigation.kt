package com.kmp.feature.login.featureUi.presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.kmp.feature.login.featureUi.presentation.ui.LoginScreen

data object LoginScreenInstance : Screen {
    @Composable
    override fun Content() {
        // Navigator: Permite navegar en pantallas dentro del feature
        //val navigator = LocalNavigator.currentOrThrow
        // Router: Permite navegar entre features
        //val router: NavigationRouter = koinInject()
        LoginScreen()
    }
}