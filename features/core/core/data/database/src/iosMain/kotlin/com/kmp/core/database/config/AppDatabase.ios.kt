/*
 * AppDatabase.ios.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.database.config

import androidx.room3.Room
import androidx.room3.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> =
    Room.databaseBuilder<AppDatabase>(
        name = applicationSupportDirectory() + "/${AppDatabase.DATABASE_NAME}",
    ).setDriver(androidx.sqlite.driver.bundled.BundledSQLiteDriver())

@OptIn(ExperimentalForeignApi::class)
private fun applicationSupportDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSApplicationSupportDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
