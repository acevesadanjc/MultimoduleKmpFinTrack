// build.gradle - coreDataStore
// Copyright (c) 2026. All rights reserved
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.koinCompiler)
}

group = "com.kmp.core"
// Version = "1.0.0"
logger.lifecycle("****************** CORE|DATA STORE ****************** ")

kotlin {

    jvmToolchain(jdkVersion = libs.versions.jvm.compileVersion.get().toInt())

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoreDataStore"
            isStatic = false
        }
    }

    android {
        namespace = "com.kmp.core.datastore"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    sourceSets {
        androidMain.dependencies {}
        commonMain.dependencies {
            // Module
            implementation(dependencyNotation = project(":core:domain"))
            implementation(dependencyNotation = project(":core:tools"))
            // Libs
            api(dependencyNotation = libs.androidx.datastore)
            implementation(dependencyNotation = libs.koin.core)
            implementation(dependencyNotation = libs.koin.annotations)
            implementation(dependencyNotation = libs.kotlinx.io.core)
        }
        iosMain.dependencies {}
        commonTest.dependencies {
            implementation(dependencyNotation = libs.kotlin.test)
        }
    }
}

koinCompiler {
    logSeverity.set("info")
}
