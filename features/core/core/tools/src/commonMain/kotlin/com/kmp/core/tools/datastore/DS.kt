/*
 * DS.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.tools.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DS {
    const val DATASTORE_NAME = "kmp_dataStore"

    object KEY {
        val USER_TOKEN = stringPreferencesKey("user_token")
        val STATUS_USER_CREDENTIALS = booleanPreferencesKey("status_user_credentials")
    }
}
