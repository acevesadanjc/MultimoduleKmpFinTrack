/*
 * DataBaseModule.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.database.di

import com.kmp.core.database.config.AppDatabase
import com.kmp.core.database.config.getDatabaseBuilder
import com.kmp.core.domain.repository.user.UserDataBaseRepository
import com.kmp.core.domain.usecase.user.UserDataBaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.kmp.core.database")
@Configuration
class CoreDataBaseModule{

    @Single
    fun appDatabase() = getDatabaseBuilder()
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

    @Single
    fun userDao(
        database: AppDatabase
    ) = database.userDao()

    @Factory
    fun userDataBaseUseCase(
        repository: UserDataBaseRepository
    ) = UserDataBaseUseCase(repository = repository)
}
