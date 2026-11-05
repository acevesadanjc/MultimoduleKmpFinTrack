/*
 * UserMapper.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.database.mapper.user

import com.kmp.core.database.entity.user.UserEntity
import com.kmp.core.domain.model.user.UserModel

fun UserEntity.toDomain(): UserModel =
    UserModel(
        id = id,
        email = email.orEmpty(),
        password = password.orEmpty(),
    )

fun UserModel.toEntity(): UserEntity =
    UserEntity(
        id = id,
        email = email,
        password = password,
    )
