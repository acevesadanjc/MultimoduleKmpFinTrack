package com.kmp.core.domain.usecase.user

import com.kmp.core.domain.repository.user.UserDataStoreRepository
import kotlinx.coroutines.flow.Flow

class UserDataStoreUseCase(
    private val repository: UserDataStoreRepository
) {

    suspend fun getUserToken(): Flow<String?> =
        repository.getUserToken()

    suspend fun saveUserToken(token: String): Flow<Unit> =
        repository.saveUserToken(token = token)

    suspend fun getStatusUserCredentials(): Flow<Boolean> =
        repository.getStatusUserCredentials()

    suspend fun saveStatusUserCredentials(status: Boolean): Flow<Unit> =
        repository.saveStatusUserCredentials(status = status)
}
