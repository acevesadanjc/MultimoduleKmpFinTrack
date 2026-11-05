package com.kmp.core.domain.repository.user

import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {
    suspend fun getUserToken(): Flow<String?>
    suspend fun getStatusUserCredentials(): Flow<Boolean>
    suspend fun saveUserToken(token: String): Flow<Unit>
    suspend fun saveStatusUserCredentials(status: Boolean): Flow<Unit>
}
