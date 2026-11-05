package com.kmp.host.di

import com.kmp.feature.splash.featureUi.di.SplashModule
import org.koin.core.annotation.KoinApplication

@KoinApplication(
    modules = [
        HostModule::class,
        AppVersionModule::class,
        SplashModule::class,
    ]
)
class HostApp
