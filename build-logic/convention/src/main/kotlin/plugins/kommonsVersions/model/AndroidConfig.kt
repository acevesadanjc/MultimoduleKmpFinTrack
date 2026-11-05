/*
 * AndroidConfig.kt
 * Copyright (c) 2026. All rights reserved
 */
package plugins.kommonsVersions.model

import plugins.kommonsVersions.KommonVersionsExtension
import plugins.kommonsVersions.extensions.configureAndroidImmediately

data class AndroidConfig(
    val namespace: String,
    val applicationId: String,
    val flavorDimensions: List<String>,
    val versionName: String,
    val versionCode: Int,
    val flavors: List<FlavorConfig>,
)

data class BuildConfigFieldConfig(
    val type: String,
    val name: String,
    val value: String,
)

data class FlavorConfig(
    val name: String,
    val dimension: String,
    val applicationIdSuffix: String,
    val versionNameSuffix: String,
    val appName: String? = null,
    val appNameSuffix: String? = null,
    val buildConfigFields: List<BuildConfigFieldConfig> = emptyList(),
)

class AndroidConfigBuilder {
    var namespace: String = ""
    var applicationId: String = ""
    var flavorDimensions: MutableList<String> = mutableListOf()
    var versionName: String = ""
    var versionCode: Int = 0
    val flavors: MutableList<FlavorConfig> = mutableListOf()

    fun flavor(block: FlavorConfigBuilder.() -> Unit) {
        val builder = FlavorConfigBuilder().apply(block)

        flavors.add(
            FlavorConfig(
                name = builder.name,
                dimension = builder.dimension,
                applicationIdSuffix = builder.applicationIdSuffix,
                versionNameSuffix = builder.versionNameSuffix,
                appName = builder.appName,
                appNameSuffix = builder.appNameSuffix,
                buildConfigFields = builder.buildConfigFields,
            )
        )
    }
}

class FlavorConfigBuilder {
    var name: String = ""
    var dimension: String = ""
    var applicationIdSuffix: String = ""
    var versionNameSuffix: String = ""
    var appName: String? = null
    var appNameSuffix: String? = null

    val buildConfigFields: MutableList<BuildConfigFieldConfig> = mutableListOf()

    fun buildConfigField(
        type: String,
        name: String,
        value: String,
    ) {
        buildConfigFields += BuildConfigFieldConfig(
            type = type,
            name = name,
            value = value,
        )
    }
}

fun KommonVersionsExtension.android(
    block: AndroidConfigBuilder.() -> Unit,
): AndroidConfig {
    val builder = AndroidConfigBuilder().apply(block)

    val config = AndroidConfig(
        namespace = builder.namespace,
        applicationId = builder.applicationId,
        flavorDimensions = builder.flavorDimensions,
        versionName = builder.versionName,
        versionCode = builder.versionCode,
        flavors = builder.flavors,
    )

    this.android.set(config)
    this.project.configureAndroidImmediately(config)

    return config
}