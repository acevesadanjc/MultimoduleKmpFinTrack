rootProject.name = "KmpCoreSDK"

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
    }
}

include(":core:domain")
include(":core:data:network")
include(":core:data:database")
include(":core:data:datastore")
include(":core:presentation:design")
include(":core:presentation:navigation")
include(":core:tools")