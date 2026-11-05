package com.kmp.host.di

import com.kmp.core.navigation.router.NavigationFeaturesRouter
import com.kmp.host.navigation.NavigationFeaturesRouterImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.kmp.host")
@Configuration
class HostModule{

    @Single
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Single
    fun provideNavigationRouter(): NavigationFeaturesRouter = NavigationFeaturesRouterImpl()

}