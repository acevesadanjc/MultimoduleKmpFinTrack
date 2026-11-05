import plugins.kommonsVersions.model.android

plugins {
    alias(libs.plugins.kommonVersions)
    alias(libs.plugins.android.application)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.koinCompiler)
}

logger.lifecycle("****************** ANDROID APP ****************** ")

// Se configura el plugin de servicios (GMS, HMS) para que se pueda usar en los flavors de la app
apply(from = "provider-services-GMS-HMS.gradle.kts")

// * Para obtener la configuracion de bloque Splash{} que esta en shared/build.gradle.kts
evaluationDependsOn(":shared")

kotlin {
    jvmToolchain(jdkVersion = libs.versions.jvm.compileVersion.get().toInt())
}

kommonVersions {
    generateFileVersions = false

    android {
        namespace = libs.versions.app.namespace.get()
        applicationId = libs.versions.app.application.id.get()
        versionCode = libs.versions.app.version.code.get().toInt()
        versionName = libs.versions.app.version.name.get()

        flavorDimensions += "market"
        flavorDimensions += "services"

        flavor {
            name = "ecuador"
            dimension = "market"
            applicationIdSuffix = ".ecuador"
            versionNameSuffix = "-ecuador"
            buildConfigField(type = "String", name = "APP_FLAVOR", value = "\"ECUADOR\"",)
        }

        flavor {
            name = "cenam"
            dimension = "market"
            applicationIdSuffix = ".cenam"
            versionNameSuffix = "-cenam"
            buildConfigField(type = "String", name = "APP_FLAVOR", value = "\"CENAM\"",)
        }

        flavor {
            name = "gms"
            dimension = "services"
            buildConfigField(type = "String", name = "APP_SERVICE", value = "\"GMS\"",)
        }

        flavor {
            name = "hms"
            dimension = "services"
            buildConfigField(type = "String", name = "APP_SERVICE", value = "\"HMS\"",)
        }
    }
}

android {

    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(projectPath = ":shared"))
    implementation(dependencyNotation = libs.bundles.androidApp.libs)
    // Configuracion de flavors (EC, CENAM)
    add(configurationName = "ecuadorImplementation", dependencyNotation = libs.bundles.features.ecuador)
    add(configurationName = "cenamImplementation", dependencyNotation = libs.bundles.features.cenam)
    // Configuracion de flavors (GMS, HMS)
    add(configurationName = "gmsImplementation", dependencyNotation = libs.bundles.apis.gms.libs)
    add(configurationName = "hmsImplementation", dependencyNotation = libs.bundles.apis.hms.libs)
}