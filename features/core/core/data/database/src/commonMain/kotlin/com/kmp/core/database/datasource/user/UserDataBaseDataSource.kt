/*
 * UserDataBaseDataSource.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.database.datasource.user

import com.kmp.core.database.entity.user.UserEntity

interface UserDataBaseDataSource {
    suspend fun clearAndInsertUser(user: UserEntity)
    suspend fun getFirstUser(): UserEntity?
    suspend fun deleteUser(user: UserEntity)
}
