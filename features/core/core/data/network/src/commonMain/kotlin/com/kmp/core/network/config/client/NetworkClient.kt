/*
 * NetworkClient.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.network.config.client

import com.kmp.core.datastore.config.AppDataStore
import io.ktor.client.HttpClient

expect fun createHttpClient(appDataStore: AppDataStore): HttpClient
