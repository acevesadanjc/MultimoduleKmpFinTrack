/*
 * AppDataStore.android.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.datastore.config

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.kmp.core.tools.datastore.DS
import okio.Path.Companion.toPath
import org.koin.mp.KoinPlatform.getKoin

actual fun createDataStore(): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            val context: Context = getKoin().get()
            context.filesDir.resolve("${DS.DATASTORE_NAME}.preferences_pb").absolutePath.toPath()
        },
    )
