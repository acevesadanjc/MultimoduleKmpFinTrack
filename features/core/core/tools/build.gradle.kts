plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.koinCompiler)
}

group = "com.kmp.core"
// Version = "1.0.0"
logger.lifecycle("****************** CORE|TOOLS ****************** ")

kotlin {

    jvmToolchain(jdkVersion = libs.versions.jvm.compileVersion.get().toInt())

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoreTools"
            isStatic = false
        }
    }

    android {
        namespace = "com.kmp.core.tools"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(dependencyNotation = libs.compose.runtime)
            implementation(dependencyNotation = libs.compose.foundation)
            implementation(dependencyNotation = libs.compose.material3)
            implementation(dependencyNotation = libs.compose.ui)
            implementation(dependencyNotation = libs.ktor.client.core)
            implementation(dependencyNotation = libs.androidx.datastore)
            implementation(dependencyNotation = libs.kotlinx.io.core)
            implementation(dependencyNotation = libs.kmp.napier)
            implementation(dependencyNotation = libs.koin.core)
            implementation(dependencyNotation = libs.koin.annotations)
        }
        commonTest.dependencies {
            implementation(dependencyNotation = libs.kotlin.test)
        }
    }
}

koinCompiler {
    logSeverity.set("info")
}
