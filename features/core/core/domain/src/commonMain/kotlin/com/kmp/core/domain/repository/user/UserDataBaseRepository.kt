package com.kmp.core.domain.repository.user

import com.kmp.core.domain.model.user.UserModel
import kotlinx.coroutines.flow.Flow

interface UserDataBaseRepository {

    suspend fun clearAndInsertUser(user: UserModel): Flow<Unit>
    suspend fun getFirstUser(): Flow<UserModel?>
    suspend fun deleteUser(user: UserModel): Flow<Unit>

}