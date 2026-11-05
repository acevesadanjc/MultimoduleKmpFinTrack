package com.kmp.core.datastore.repository.user

import com.kmp.core.datastore.datasource.user.UserDataStoreSource
import com.kmp.core.domain.repository.user.UserDataStoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Factory

@Factory
class UserDataStoreRepositoryImpl(
    private val usersDataStoreSource: UserDataStoreSource,
    private val ioDispatcher: CoroutineDispatcher,
): UserDataStoreRepository {

    override suspend fun getUserToken(): Flow<String?> =
        usersDataStoreSource.getUserToken().flowOn(context = ioDispatcher)

    override suspend fun getStatusUserCredentials(): Flow<Boolean> =
        usersDataStoreSource.getStatusUserCredentials().flowOn(context = ioDispatcher)

    override suspend fun saveUserToken(token: String): Flow<Unit> = flow {
        emit(value = usersDataStoreSource.saveUserToken(token = token))
    }.flowOn(context = ioDispatcher)

    override suspend fun saveStatusUserCredentials(status: Boolean): Flow<Unit> = flow {
        emit(value = usersDataStoreSource.saveStatusUserCredentials(status = status))
    }.flowOn(context = ioDispatcher)

}