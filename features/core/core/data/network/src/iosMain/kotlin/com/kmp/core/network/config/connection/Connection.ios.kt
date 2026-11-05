/*
 * Connection.ios.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.network.config.connection

import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.ConnectivityProvider
import dev.jordond.connectivity.asProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfied
import platform.darwin.dispatch_get_main_queue

actual fun getConnectivityProvider(): ConnectivityProvider =
    callbackFlow {
        val monitor = nw_path_monitor_create()
        nw_path_monitor_set_update_handler(monitor) { path ->
            val status = if (nw_path_get_status(path) == nw_path_status_satisfied) {
                Connectivity.Status.Connected(false)
            } else {
                Connectivity.Status.Disconnected
            }
            trySend(status)
        }
        nw_path_monitor_set_queue(monitor, dispatch_get_main_queue())
        nw_path_monitor_start(monitor)
        awaitClose { nw_path_monitor_cancel(monitor) }
    }.asProvider()
