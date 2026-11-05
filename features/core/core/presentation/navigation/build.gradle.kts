plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.android.lint)
}

group = "com.kmp.core"
//version = "1.0.0"
logger.lifecycle("****************** CORE|NAVIGATION ****************** ")

kotlin {

    jvmToolchain(jdkVersion = libs.versions.jvm.compileVersion.get().toInt())

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoreNavigation"
            isStatic = false
        }
    }

    android {
        namespace = "com.kmp.core.navigation"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    sourceSets {
        androidMain.dependencies {}
        commonMain.dependencies {
            implementation(dependencyNotation = libs.kotlinx.coroutines.core)
            api(dependencyNotation = libs.voyager.navigator)
            api(dependencyNotation = libs.voyager.transitions)
        }
        iosMain.dependencies {}
        commonTest.dependencies {
            implementation(dependencyNotation = libs.kotlin.test)
        }
    }
}
