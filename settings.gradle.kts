rootProject.name = "HostApp"

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

/*
* Configuramos el plugin en settings con la finalidad de que el JDK se descargue local y evitamos warnings con gradle menor a 10
* jvmToolchain(jdkVersion = 21) para uso en gradle
*/
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven(url = "https://developer.huawei.com/repo/")
    }
}

//Build-logic
includeBuild("build-logic")

//Features
includeBuild("features/splash")

include(":androidApp")
include(":shared")