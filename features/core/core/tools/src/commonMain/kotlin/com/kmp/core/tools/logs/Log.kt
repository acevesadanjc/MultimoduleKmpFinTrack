/*
 * KmpLogger.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.tools.logs

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.Napier

object Log {

    fun debug(
        message: String,
        tag: String? = null,
    ) {
        Napier.d(
            message = message,
            tag = tag,
        )
    }

    fun info(
        message: String,
        tag: String? = null,
    ) {
        Napier.i(
            message = message,
            tag = tag,
        )
    }

    fun error(
        message: String,
        tag: String? = null,
        throwable: Throwable? = null,
    ) {
        Napier.e(
            throwable = throwable,
            message = message,
            tag = tag,
        )
    }

    fun network(
        message: String,
        tag: String? = null,
    ) {
        Napier.d(
            message = message,
            tag = tag,
        )
    }
}
