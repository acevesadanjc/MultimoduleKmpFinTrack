rootProject.name = "login"

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

// Build Core
includeBuild("../core")
// Feature
include(":login")
//--> Si es Copia de feature descomentar y eliminar el de arriba
//include(":[bone]-[flavor]")
//project(":[bone]-[flavor]").projectDir = file("[bone]")
