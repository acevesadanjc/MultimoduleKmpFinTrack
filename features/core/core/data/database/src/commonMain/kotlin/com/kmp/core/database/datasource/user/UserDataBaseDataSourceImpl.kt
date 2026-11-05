/*
 * UserDataBaseDataSourceImpl.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.database.datasource.user

import com.kmp.core.database.dao.users.UsersDao
import com.kmp.core.database.entity.user.UserEntity
import org.koin.core.annotation.Factory

@Factory
class UserDataBaseDataSourceImpl(
    private val usersDao: UsersDao,
): UserDataBaseDataSource {

    // Data base
    override suspend fun clearAndInsertUser(user: UserEntity) =
        usersDao.clearAndInsertUser(user = user)

    override suspend fun getFirstUser(): UserEntity? =
        usersDao.getFirstUser()

    override suspend fun deleteUser(user: UserEntity) =
        usersDao.deleteUser(user = user)
}
