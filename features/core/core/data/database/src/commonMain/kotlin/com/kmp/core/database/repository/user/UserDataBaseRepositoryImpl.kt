package com.kmp.core.database.repository.user

import com.kmp.core.database.datasource.user.UserDataBaseDataSource
import com.kmp.core.database.mapper.user.toDomain
import com.kmp.core.database.mapper.user.toEntity
import com.kmp.core.domain.model.user.UserModel
import com.kmp.core.domain.repository.user.UserDataBaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Factory

@Factory
class UserDataBaseRepositoryImpl(
    private val userDataBaseDataSource: UserDataBaseDataSource,
    private val ioDispatcher: CoroutineDispatcher,
): UserDataBaseRepository {

    override suspend fun clearAndInsertUser(user: UserModel): Flow<Unit> = flow {
        emit(value = userDataBaseDataSource.clearAndInsertUser(user = user.toEntity()))
    }.flowOn(context = ioDispatcher)

    override suspend fun getFirstUser(): Flow<UserModel?> = flow {
        emit(value = userDataBaseDataSource.getFirstUser()?.toDomain())
    }.flowOn(context = ioDispatcher)

    override suspend fun deleteUser(user: UserModel): Flow<Unit> = flow {
        emit(value = userDataBaseDataSource.deleteUser(user = user.toEntity()))
    }.flowOn(context = ioDispatcher)
}