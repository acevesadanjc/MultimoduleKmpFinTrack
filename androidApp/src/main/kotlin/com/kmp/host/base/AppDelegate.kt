/*
 * AppDelegate.kt
 * Copyright (c) 2026. All rights reserved
*/
package com.kmp.host.base

import android.app.Application
import com.kmp.host.BuildConfig
import com.kmp.host.analytics.AnalyticsInitializer
import com.kmp.host.di.initKoin
import com.kommon.versions.initializeAndroidFlavor
import com.kommon.versions.initializeAndroidBuildType
import com.kommon.versions.initializeAndroidFlavorServices
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class AppDelegate: Application() {

    override fun onCreate() {
        super.onCreate()
        // Seteamos el flavor de Android en KommonVersions
        initializeAndroidFlavor(BuildConfig.APP_FLAVOR)
        // Seteamos el flavor de servicios (GMS/HMS) de Android en KommonVersions
        initializeAndroidFlavorServices(BuildConfig.APP_SERVICE)
        // Seteamos el tipo de build de Android en KommonVersions
        initializeAndroidBuildType(BuildConfig.BUILD_TYPE)
        // LOGS KMP
        Napier.base(DebugAntilog())
        // Inicializamos Analytics (HMS / GMS)
        AnalyticsInitializer.initialize(this)
        // DI KMP
        initKoin(
            config = {
                androidLogger(Level.DEBUG)
                androidContext(this@AppDelegate)
            },
        )
    }
}
