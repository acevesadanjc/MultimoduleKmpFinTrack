package com.kmp.host.di

import com.kmp.feature.splash.featureUi.di.SplashModule
import com.kmp.feature.login.featureUi.di.LoginModule
import org.koin.core.annotation.KoinApplication

@KoinApplication(
    modules = [
        HostModule::class,
        AppVersionModule::class,
        SplashModule::class,
        LoginModule::class
    ]
)
class HostApp
