package com.kmp.host.di

import com.kommon.versions.KommonVersions
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
class AppVersionModule {

    @Single
    @Named("buildType")
    fun buildType(): String =
        KommonVersions.BUILD_TYPE

    @Single
    @Named("flavor")
    fun flavor(): String =
        KommonVersions.FLAVOR

    @Single
    @Named("flavorServices")
    fun flavorServices(): String =
        KommonVersions.FLAVOR_SERVICES

    @Single
    @Named("versionCode")
    fun versionCode(): Int =
        KommonVersions.VERSION_CODE

    @Single
    @Named("versionName")
    fun versionName(): String =
        KommonVersions.VERSION_NAME

}