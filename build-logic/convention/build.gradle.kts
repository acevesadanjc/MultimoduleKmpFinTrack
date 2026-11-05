// build.gradle - Kmp build-logic:convention
// Copyright (c) 2026. All rights reserved
plugins {
    `kotlin-dsl`
}

group = "com.kmp.convention.buildlogic"

logger.lifecycle("****************** BUILD-LOGIC ****************** ")

kotlin {
    jvmToolchain(jdkVersion = 21)
}

dependencies {
    compileOnly(libs.android.gradle.convention)
    compileOnly(libs.kotlin.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kmpCommonVersion") {
            id = "com.kmp.commonversion"
            implementationClass = "plugins.kommonsVersions.KommonVersionExtension"
        }
    }
}
