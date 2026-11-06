/*
 * AppDataStore.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.datastore.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.kmp.core.tools.datastore.DS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException
import org.koin.core.annotation.Single

@Single
class AppDataStore(
    private val dataStore: DataStore<Preferences>,
) {

    val userToken: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { data -> data[DS.KEY.USER_TOKEN] }

    val statusUserCredentials: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { data -> data[DS.KEY.STATUS_USER_CREDENTIALS] ?: false }

    suspend fun saveToken(token: String) {
        dataStore.edit { data -> data[DS.KEY.USER_TOKEN] = token }
    }

    suspend fun removeToken() {
        dataStore.edit { preferences ->
            preferences.remove(DS.KEY.USER_TOKEN)
        }
    }

    suspend fun saveStatusUserCredentials(status: Boolean) {
        dataStore.edit { data -> data[DS.KEY.STATUS_USER_CREDENTIALS] = status }
    }
}

expect fun createDataStore(): DataStore<Preferences>
