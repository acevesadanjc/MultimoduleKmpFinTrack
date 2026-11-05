import com.infinum.arkive.plugin.extensions.SnapshotRetention
// build.gradle - coreDesign
// Copyright (c) 2026. All rights reserved

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.arkive)
}

group = "com.kmp.core"
// Version = "1.0.0"
logger.lifecycle("****************** CORE|DESIGN ****************** ")

arkive {
    enableVariants.set(false)
    enablePreviewParameters.set(false)
    designFileKey.set("TF9QyqumvDAJZ30CgduIKS")
    snapshotRetention.set(SnapshotRetention.BASE)
    engine(Roborazzi)
}

kotlin {

    jvmToolchain(jdkVersion = libs.versions.jvm.compileVersion.get().toInt())

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoreDesign"
            isStatic = false
        }
    }

    android {
        namespace = "com.kmp.core.design"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        androidResources {
            enable = true
        }

        withHostTestBuilder {}.configure {
            isIncludeAndroidResources = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            api(libs.compose.uiTooling)
        }
        commonMain.dependencies {
            // Module
            implementation(dependencyNotation = project(path = ":core:tools"))
            // Libs
            api(dependencyNotation = libs.compose.runtime)
            api(dependencyNotation = libs.compose.foundation)
            api(dependencyNotation = libs.compose.material3)
            api(dependencyNotation = libs.compose.ui)
            api(dependencyNotation = libs.compose.uiToolingPreview)
            api(dependencyNotation = libs.compose.coil.network)
            api(dependencyNotation = libs.compose.coil.filekit)
            // Este no se comparte con api debe ser exclusivo por modulo
            implementation(dependencyNotation = libs.compose.components.resources)
        }
        iosMain.dependencies {}
        commonTest.dependencies {
            implementation(dependencyNotation = libs.kotlin.test)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}
