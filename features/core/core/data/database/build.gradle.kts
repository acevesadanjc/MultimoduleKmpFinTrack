// build.gradle - coreDataBase
// Copyright (c) 2026. All rights reserved
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.koinCompiler)
}

group = "com.kmp.core"
// Version = "1.0.0"
logger.lifecycle("****************** CORE|DATABASE ****************** ")

kotlin {

    jvmToolchain(jdkVersion = libs.versions.jvm.compileVersion.get().toInt())

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoreDatabase"
            linkerOpts.add("-lsqlite3") // Requerido para Room en iOS
            isStatic = false
        }
    }

    android {
        namespace = "com.kmp.core.database"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    sourceSets {
        androidMain.dependencies {}
        commonMain.dependencies {
            // Module
            implementation(dependencyNotation = project(path = ":core:domain"))
            // Libs
            api(dependencyNotation = libs.androidx.room.runtime)
            api(dependencyNotation = libs.androidx.sqlite.bundled)
            implementation(dependencyNotation = libs.koin.core)
            implementation(dependencyNotation = libs.koin.annotations)
        }
        iosMain.dependencies {}
        commonTest.dependencies {
            implementation(dependencyNotation = libs.kotlin.test)
        }
    }

}

dependencies {
    // Compilers Room
    listOf("kspAndroid", "kspIosArm64", "kspIosSimulatorArm64", "kspCommonMainMetadata")
        .forEach { kspName ->
            add(configurationName = kspName, dependencyNotation = libs.androidx.room.compiler)
        }
}

koinCompiler {
    logSeverity.set("info")
}

room3 {
    schemaDirectory(path = "$projectDir/schemas")
}
