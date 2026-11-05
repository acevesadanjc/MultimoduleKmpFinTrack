/*
 * UserEntity.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.database.entity.user

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String?,
    var password: String?,
)