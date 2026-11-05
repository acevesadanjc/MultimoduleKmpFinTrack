/*
 * UserDataStoreSource.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.datastore.datasource.user

import kotlinx.coroutines.flow.Flow

interface UserDataStoreSource {
    suspend fun getUserToken(): Flow<String?>
    suspend fun saveUserToken(token: String)
    suspend fun getStatusUserCredentials(): Flow<Boolean>
    suspend fun saveStatusUserCredentials(status: Boolean)
}
