package com.kmp.core.network.di

import com.kmp.core.datastore.config.AppDataStore
import com.kmp.core.domain.repository.login.LoginNetworkRepository
import com.kmp.core.domain.usecase.login.LoginNetworkUseCase
import com.kmp.core.network.config.client.createHttpClient
import com.kmp.core.network.config.connection.getConnectivityProvider
import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.ConnectivityProvider
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.kmp.core.network")
@Configuration
class CoreNetworkModule {

    @Single
    fun httpClient(
        appDataStore: AppDataStore
    ) = createHttpClient(appDataStore = appDataStore)

    @Single
    fun connectivityProvider() = getConnectivityProvider()

    @Single
    fun connectivity(
        provider: ConnectivityProvider
    ) = Connectivity(provider = provider)

    @Factory
    fun loginNetworkUseCase(
        repository: LoginNetworkRepository
    ) = LoginNetworkUseCase(repository = repository)
}
