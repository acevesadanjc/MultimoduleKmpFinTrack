/*
 * KoinInitializer.kt
 * Copyright (c) 2026. All rights reserved
*/
package com.kmp.host.di

import org.koin.dsl.KoinAppDeclaration
import org.koin.plugin.module.dsl.startKoin

fun initKoin(
    config: KoinAppDeclaration? = null,
) {
    startKoin<HostApp> {
        config?.invoke(this)
    }
}
