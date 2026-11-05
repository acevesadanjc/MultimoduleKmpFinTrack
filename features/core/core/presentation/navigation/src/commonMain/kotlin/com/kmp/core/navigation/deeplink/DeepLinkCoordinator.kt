package com.kmp.core.navigation.deeplink

import com.kmp.core.navigation.enum.DeepLinkDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object DeepLinkCoordinator {

    private val _pending = MutableStateFlow<DeepLinkDestination?>(null)
    val pending: StateFlow<DeepLinkDestination?> = _pending.asStateFlow()

    /**
     * Guardamos la ruta del deep link en el flujo de estado para su uso posterior.
     * @param url La URL del deep link.
     * @return true si la URL es válida y se guardó correctamente, false en caso contrario.
     */
    fun saveDeepLinkRoute(url: String): Boolean{
        val destination = DeepLinkParser.parse(url) ?: return false
        _pending.value = destination
        return true
    }

    /**
     * Obtenemos la ruta del deep link guardado y la eliminamos del flujo de estado.
     * @return La ruta del deep link si existe, null en caso contrario.
     */
    fun getDeepLinkRoute(): DeepLinkDestination?{
        val destination = _pending.value
        _pending.value = null
        return destination
    }

}