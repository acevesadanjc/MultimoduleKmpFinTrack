/*
 * UserDataStoreSourceImpl.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.datastore.datasource.user

import com.kmp.core.datastore.config.AppDataStore
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class UserDataStoreSourceImpl(
    private val dataStore: AppDataStore,
): UserDataStoreSource {

    override suspend fun getUserToken(): Flow<String?> =
        dataStore.userToken

    override suspend fun saveUserToken(token: String) =
        dataStore.saveToken(token)

    override suspend fun getStatusUserCredentials(): Flow<Boolean> =
        dataStore.statusUserCredentials

    override suspend fun saveStatusUserCredentials(status: Boolean) =
        dataStore.saveStatusUserCredentials(status)
}
