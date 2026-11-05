/*
 * DataStoreModule.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.datastore.di

import com.kmp.core.datastore.config.createDataStore
import com.kmp.core.domain.repository.user.UserDataStoreRepository
import com.kmp.core.domain.usecase.user.UserDataStoreUseCase
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.kmp.core.datastore")
@Configuration
class CoreDataStoreModule{

    @Single
    fun dataStore() = createDataStore()

    @Factory
    fun userDataStoreUseCase(
        repository: UserDataStoreRepository
    ) = UserDataStoreUseCase(repository = repository)
}
