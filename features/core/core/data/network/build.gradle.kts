// build.gradle - coreNetwork
// Copyright (c) 2026. All rights reserved

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.koinCompiler)
}

group = "com.kmp.core"
//version = "1.0.0"
logger.lifecycle("****************** CORE|NETWORK ****************** ")

kotlin {

    jvmToolchain(jdkVersion = libs.versions.jvm.compileVersion.get().toInt())

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoreNetwork"
            isStatic = false
        }
    }

    android {
        namespace = "com.kmp.core.network"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    sourceSets {
        androidMain.dependencies {
            implementation(dependencyNotation = libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(dependencyNotation = project(":core:data:datastore"))
            implementation(dependencyNotation = project(":core:domain"))
            implementation(dependencyNotation = project(":core:tools"))
            api(dependencyNotation = libs.ktor.client.logging)
            api(dependencyNotation = libs.jordond.connectivity)
            api(dependencyNotation = libs.ktor.client.core)
            api(dependencyNotation = libs.ktor.auth)
            api(dependencyNotation = libs.ktor.client.content.negotiation)
            api(dependencyNotation = libs.ktor.serialization.kotlinx.json)
            api(dependencyNotation = libs.okhttp.profiler)
            implementation(dependencyNotation = libs.koin.core)
            implementation(dependencyNotation = libs.koin.annotations)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

koinCompiler {
    logSeverity.set("info")
}