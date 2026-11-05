/*
 * AppDatabase.android.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.database.config

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase
import org.koin.mp.KoinPlatform.getKoin

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>{
    val context = getKoin().get<Context>()
    val dbFile = context.getDatabasePath(AppDatabase.DATABASE_NAME)
    return Room.databaseBuilder<AppDatabase>(
        context = context,
        name = dbFile.absolutePath,
    )
}
