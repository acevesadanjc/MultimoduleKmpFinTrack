/*
 * KoinIOS.kt
 * Copyright (c) 2026. All rights reserved
*/
package com.kmp.host

import com.kommon.versions.initializeIosFlavor
import com.kmp.host.di.initKoin

fun initKoinIOS(flavorCode: String) {
    initializeIosFlavor(flavorCode)
    initKoin()
}
