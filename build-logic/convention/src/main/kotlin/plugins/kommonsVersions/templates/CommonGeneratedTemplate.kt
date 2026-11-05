/*
 * CommonGeneratedTemplate.kt
 * Copyright (c) 2026. All rights reserved
 */
package plugins.kommonsVersions.templates

val commonGeneratedTemplate = """
    /*
     * KommonVersions.common.kt
     * Copyright (c) 2026. All rights reserved
     */
    package com.kommon.versions

    expect object KommonVersions {
        val BUILD_TYPE: String
        val FLAVOR: String
        val FLAVOR_SERVICES: String
        val VERSION_CODE: Int
        val VERSION_NAME: String
    }
""".trimIndent()