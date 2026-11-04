// build.gradle - Login
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
logger.lifecycle("****************** FEATURE|LOGIN ****************** ")

// Nos permite poder usar los recursos en caso de separar el Feature por flavor
compose.resources {
    packageOfResClass = "com.kmp.feature.login.generated.resources"
}

kotlin {

    jvmToolchain(jdkVersion = libs.versions.jvm.compileVersion.get().toInt())

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "FeatureLogin"
            isStatic = false
        }
    }

    android {
        namespace = "com.kmp.feature.login"
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
            implementation(libs.bundles.feature.base.libs)
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