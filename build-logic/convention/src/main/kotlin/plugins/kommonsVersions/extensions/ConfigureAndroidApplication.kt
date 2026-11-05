/*
 * ConfigureAndroidApplication.kt
 * Copyright (c) 2026. All rights reserved
 */
package plugins.kommonsVersions.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import plugins.kommonsVersions.model.AndroidConfig

fun Project.configureAndroidImmediately(config: AndroidConfig) {

    pluginManager.withPlugin("com.android.application") {
        extensions.configure<ApplicationExtension> {
            namespace = config.namespace

            defaultConfig {
                applicationId = config.applicationId
                versionCode = config.versionCode
                versionName = config.versionName
            }

            if (config.flavorDimensions.isNotEmpty()) {
                flavorDimensions.clear()
                flavorDimensions.addAll(config.flavorDimensions)
            }

            productFlavors {
                config.flavors.forEach { flavorConfig ->
                    create(flavorConfig.name) {
                        dimension = flavorConfig.dimension
                        applicationIdSuffix = flavorConfig.applicationIdSuffix
                        versionNameSuffix = flavorConfig.versionNameSuffix

                        flavorConfig.appName?.let { appName ->
                            manifestPlaceholders["app_name"] = appName
                        }

                        flavorConfig.appNameSuffix?.let { appNameSuffix ->
                            manifestPlaceholders["app_name_suffix"] = appNameSuffix
                        }

                        flavorConfig.buildConfigFields.forEach { field ->
                            buildConfigField(
                                field.type,
                                field.name,
                                field.value,
                            )
                        }
                    }
                }
            }
        }
    }

    pluginManager.withPlugin("com.android.kotlin.multiplatform.library") {
        extensions.configure<KotlinMultiplatformExtension>("kotlin") {
            (this as ExtensionAware)
                .extensions
                .configure<KotlinMultiplatformAndroidLibraryExtension>("android") {
                    namespace = config.namespace
                }
        }
    }
}