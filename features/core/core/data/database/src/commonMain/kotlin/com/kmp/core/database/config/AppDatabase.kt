/*
 * AppDatabase.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.database.config

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.kmp.core.database.dao.users.UsersDao
import com.kmp.core.database.entity.user.UserEntity

@Database(
    entities = [
        UserEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UsersDao

    companion object {
        const val DATABASE_NAME = "kmpDB"
    }
}

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
