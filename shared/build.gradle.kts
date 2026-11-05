import io.kmpbits.splash.ExitAnimation
import io.kmpbits.splash.SplashColor
import io.kmpbits.splash.SplashLogo
import plugins.kommonsVersions.model.android
import plugins.kommonsVersions.model.ios
import plugins.kommonsVersions.model.schema

plugins {
    alias(libs.plugins.kommonVersions)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.koinCompiler)
    alias(libs.plugins.kmpSplash)
}

group = "com.kmp.host"
// Version = "1.0.0"
logger.lifecycle("****************** MAIN HOST ****************** ")

compose.resources {
    packageOfResClass = "com.kmp.host.shared.generated.resources"
}

//** Configuración de la pantalla de splash para Android e iOS
splashScreen {
    backgroundColor = SplashColor.hex(value = "#345CDE")
    logo = SplashLogo.resource(fileName = "splash_logo.png")
    exitAnimation = ExitAnimation.FadeOut(durationMs = 500)
    androidAppPath = "androidApp"
    iosProjectPath = "iosApp/iosApp"
    resourcePackage = "com.kmp.host.shared.generated.resources"
}

kommonVersions {
    generateFileVersions = true

    android {
        namespace = "com.kmp.host.shared"
        applicationId = libs.versions.app.application.id.get()
        versionCode = libs.versions.app.version.code.get().toInt()
        versionName = libs.versions.app.version.name.get()
    }

    ios {
        projectFolderName = "iosApp"
        appName = libs.versions.app.name.get()
        bundleId = libs.versions.app.application.id.get()
        marketingVersion = libs.versions.app.version.name.get()
        currentVersion = libs.versions.app.version.code.get()

        schemas += schema {
            name = "Ecuador"
            bundleSuffix = "ecuador"
            nameSuffix = " Ecuador"
        }

        schemas += schema {
            name = "Cenam"
            bundleSuffix = "cenam"
            nameSuffix = " Cenam"
        }
    }
}

kotlin {

    // Evitamos los warnings de "expect/actual" para las clases que se comparten entre Android e iOS.
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    jvmToolchain(jdkVersion = libs.versions.jvm.compileVersion.get().toInt())

    //**** CONFIG IOS ************************************************************************//
    val iosTargets = listOf(
        iosArm64(),
        iosSimulatorArm64(),
    )

    iosTargets.forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = false
        }
    }

    // Obtenemos el flavor del Scheme de iOS que se está compilando.
    val iosFlavor = providers.gradleProperty("iosFlavor")
        .map { it.trim().uppercase() }
        .orElse("ECUADOR") //Puede variar para compílar en IDE la carpeta iosEcuadorMain o iosCenamMain.
        .get()

    // Generamos los source sets específicos para cada flavor de iOS.
    val selectedIosSourceSetName = when (iosFlavor) {
        "ECUADOR" -> "iosEcuadorMain"
        "CENAM" -> "iosCenamMain"
        else -> error("Unsupported iOS flavor: $iosFlavor")
    }
    //**************************************************************************************//

    android {
        namespace = "com.kmp.host.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        androidResources {
            enable = true
        }

        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    sourceSets {
        // Creamos un source set común para iOS que dependa de commonMain.
        val iosMain = create("iosMain") { dependsOn(named("commonMain").get()) }
        // Creamos los source sets específicos para cada flavor de iOS que dependan del source set común iosMain.
        create(selectedIosSourceSetName) {
            dependsOn(iosMain)
        }

        commonMain.dependencies {
            implementation(dependencyNotation = libs.bundles.commonApp.libs)
        }
        iosMain.dependencies {
            implementation(dependencyNotation = libs.bundles.iosApp.libs)
        }
        commonTest.dependencies {
            implementation(dependencyNotation = libs.kotlin.test)
        }
    }

    // ** Config iOS Flavor *********************************************************************//
    val selectedIosSourceSet = sourceSets
        .named(selectedIosSourceSetName)
        .get()

    iosTargets.forEach { iosTarget ->
        iosTarget
            .compilations
            .getByName("main")
            .defaultSourceSet
            .dependsOn(selectedIosSourceSet)
    }

    selectedIosSourceSet.dependencies {
        when (iosFlavor) {
            "ECUADOR" -> { implementation(dependencyNotation = libs.bundles.features.ecuador) }
            "CENAM"   -> { implementation(dependencyNotation = libs.bundles.features.cenam) }
        }
    }
    //*******************************************************************************************//
}
