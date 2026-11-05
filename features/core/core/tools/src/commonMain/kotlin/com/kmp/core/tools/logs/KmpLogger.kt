/*
 * KmpLogger.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.tools.logs

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.Napier

object KmpLogger {

    fun init(antilog: Antilog) {
        Napier.base(antilog)
    }

    fun log(
        message: String,
        tag: String? = null,
        typeLog: TypeLog
    ) {
        when(typeLog){
            TypeLog.DEBUG, TypeLog.NETWORK -> {
                Napier.d(message = message, tag = tag)
            }
            TypeLog.INFO    -> { Napier.i(message = message, tag = tag) }
            TypeLog.ERROR   -> { Napier.e(message = message, tag = tag) }
        }
    }
}
