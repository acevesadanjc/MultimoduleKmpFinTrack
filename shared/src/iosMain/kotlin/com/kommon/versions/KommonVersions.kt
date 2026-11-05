/*
 * KommonVersions.ios.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kommon.versions

import platform.Foundation.NSBundle
import kotlin.experimental.ExperimentalNativeApi

private var iosFlavor: String? = null

fun initializeIosFlavor(flavor: String) {
    require(flavor.isNotBlank()) { "iOS flavor cannot be blank." }
    iosFlavor = flavor.trim().uppercase()
}

@OptIn(ExperimentalNativeApi::class)
actual object KommonVersions {

    private const val CF_BUNDLE_VERSION_STRING = "CFBundleShortVersionString"
    private const val CF_BUNDLE_CODE = "CFBundleVersion"

    private val bundleMap = NSBundle.mainBundle.infoDictionary

    private fun getString(
        key: String,
        default: String = "",
    ): String = (bundleMap?.get(key) as? String) ?: default

    private val isDebug = Platform.isDebugBinary

    private val nativeBuildType: String =
        if (isDebug) "debug" else "release"

    actual val FLAVOR: String
        get() = checkNotNull(iosFlavor) {
            "iOS flavor has not been initialized."
        }
    
    actual val FLAVOR_SERVICES: String
        get() = "iOS"

    actual val BUILD_TYPE: String
        get() = "$FLAVOR - $nativeBuildType"

    actual val VERSION_CODE: Int =
        getString(CF_BUNDLE_CODE).toIntOrNull() ?: 1

    actual val VERSION_NAME: String =
        getString(CF_BUNDLE_VERSION_STRING)
}