// build.gradle - Splash
// Copyright (c) 2026. All rights reserved

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.koinCompiler)
}

group = "com.kmp.feature"
// Version = "1.0.0"
logger.lifecycle("****************** FEATURE|SPLASH ****************** ")

compose.resources {
    packageOfResClass = "com.kmp.feature.splash.generated.resources"
}

kotlin {

    jvmToolchain(jdkVersion = libs.versions.jvm.compileVersion.get().toInt())

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "FeatureSplash"
            isStatic = false
        }
    }

    android {
        namespace = "com.kmp.feature.splash"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        androidResources {
            enable = true
        }

        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    sourceSets {
        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(dependencyNotation = libs.bundles.feature.base.libs)
            // Se comparten con Host a fin de configurar sus KoinModules en HOST
            api(dependencyNotation = libs.core.database)
            api(dependencyNotation = libs.core.datastore)
            api(dependencyNotation = libs.core.network)
            api(dependencyNotation = libs.core.navigation)
            api(dependencyNotation = libs.core.design)
            api(dependencyNotation = libs.core.tools)
        }
        iosMain.dependencies {

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

koinCompiler {
    logSeverity.set("info")
}