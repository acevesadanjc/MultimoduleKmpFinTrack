package com.kmp.core.tools.network.enums

enum class CodeServices(val code: Int) {
    RESPONSE_OK(200),
    USER_NOT_REGISTER(203),
    UNKNOWN_CODE(-1); // Código por defecto para errores no mapeados

    companion object {
        // Función para encontrar el enum a partir del número del JSON
        fun fromCode(code: Int?): CodeServices {
            return entries.find { it.code == code } ?: UNKNOWN_CODE
        }
    }
}