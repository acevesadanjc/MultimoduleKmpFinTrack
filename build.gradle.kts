buildscript {
    repositories {
        maven(url = "https://developer.huawei.com/repo/")
    }
    dependencies {
        classpath(libs.huawei.agconnect.plugin)
    }
}

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.koinCompiler) apply false
    alias(libs.plugins.kmpSplash) apply false
    alias(libs.plugins.google.services) apply false
}