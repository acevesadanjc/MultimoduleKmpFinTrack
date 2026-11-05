plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.android.lint)
}

group = "com.kmp.core"

kotlin {
    jvmToolchain(jdkVersion = libs.versions.jvm.compileVersion.get().toInt())

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoreDomain"
            isStatic = false
        }
    }

    android {
        namespace = "com.kmp.core.domain"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(dependencyNotation = project(":core:tools"))
            implementation(dependencyNotation = libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(dependencyNotation = libs.kotlin.test)
        }
    }
}
