package com.kmp.core.domain.usecase.user

import com.kmp.core.domain.model.user.UserModel
import com.kmp.core.domain.repository.user.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow

class UserDataBaseUseCase(
    private val repository: UserDataBaseRepository
) {

    suspend fun clearAndInsertUser(
        email: String,
        password: String,
    ): Flow<Unit> = repository.clearAndInsertUser(
        user = UserModel(
            email = email,
            password = password
        )
    )

    suspend fun getFirstUser(): Flow<UserModel?> = repository.getFirstUser()

    suspend fun deleteUser(user: UserModel): Flow<Unit> = repository.deleteUser(user = user)
}
