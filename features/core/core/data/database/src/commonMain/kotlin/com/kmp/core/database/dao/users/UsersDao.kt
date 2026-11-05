/*
 * UsersDao.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.database.dao.users

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import com.kmp.core.database.entity.user.UserEntity

@Dao
interface UsersDao {

    @Transaction
    suspend fun clearAndInsertUser(user: UserEntity) {
        deleteAllUsers()
        insertUser(user)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getFirstUser(): UserEntity?

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}
