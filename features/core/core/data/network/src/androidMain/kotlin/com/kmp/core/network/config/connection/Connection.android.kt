/*
 * Connection.android.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.network.config.connection

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.ConnectivityProvider
import dev.jordond.connectivity.asProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.koin.mp.KoinPlatform.getKoin

@SuppressLint("MissingPermission")
actual fun getConnectivityProvider(): ConnectivityProvider {
    val context = getKoin().get<Context>()
    return callbackFlow {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(Connectivity.Status.Connected(false))
            }
            override fun onLost(network: Network) {
                trySend(Connectivity.Status.Disconnected)
            }
        }
        manager.registerDefaultNetworkCallback(callback)
        awaitClose { manager.unregisterNetworkCallback(callback) }
    }.asProvider()
}
