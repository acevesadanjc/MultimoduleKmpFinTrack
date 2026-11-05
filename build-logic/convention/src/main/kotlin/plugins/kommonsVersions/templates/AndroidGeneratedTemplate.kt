/*
 * AndroidGeneratedTemplate.kt
 * Copyright (c) 2026. All rights reserved
 */
package plugins.kommonsVersions.templates

fun getAndroidGeneratedTemplate(
    versionCode: Int,
    versionName: String,
) = """
    /*
     * KommonVersions.android.kt
     * Copyright (c) 2026. All rights reserved
     */
    package com.kommon.versions

    @Volatile
    private var androidBuildType: String? = null
    @Volatile
    private var androidFlavor: String? = null
    @Volatile
    private var androidFlavorServices: String? = null

    fun initializeAndroidFlavor(flavor: String) {
        require(flavor.isNotBlank()) { "Android flavor cannot be blank." }
        androidFlavor = flavor.trim().uppercase()
    }

    fun initializeAndroidFlavorServices(flavorServices: String) {
        require(flavorServices.isNotBlank()) { "Android flavor cannot be blank." }
        androidFlavorServices = flavorServices.trim().uppercase()
    }

    fun initializeAndroidBuildType(buildType: String) {
        require(buildType.isNotBlank()) { "Android build type cannot be blank." }
        androidBuildType = buildType
    }

    actual object KommonVersions {
        actual val BUILD_TYPE: String
            get() = checkNotNull(androidBuildType) {
                "Android build type has not been initialized."
            }
        actual val FLAVOR: String
            get() = checkNotNull(androidFlavor) {
                "Android flavor has not been initialized."
            }
        actual val FLAVOR_SERVICES: String
            get() = checkNotNull(androidFlavorServices) {
                "Android flavor services has not been initialized."
            }
        actual val VERSION_CODE: Int = $versionCode
        actual val VERSION_NAME: String = "$versionName"
    }
""".trimIndent()