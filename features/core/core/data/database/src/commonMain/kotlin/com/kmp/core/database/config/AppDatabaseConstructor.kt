/*
 * AppDatabaseConstructor.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.database.config

import androidx.room3.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor: RoomDatabaseConstructor<AppDatabase>{
    override fun initialize(): AppDatabase
}
