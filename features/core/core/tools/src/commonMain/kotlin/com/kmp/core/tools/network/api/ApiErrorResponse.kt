/*
 * ApiErrorResponse.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.tools.network.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelo de Transferencia de Datos (DTO) principal para mapear respuestas de error enviadas por la API.
 *
 * @property error Carga útil ([ErrorPayload]) con la información detallada del error, o `null` si la respuesta no sigue esta estructura.
 */
@Serializable
data class ApiErrorResponse(
    val error: ErrorPayload? = null,
)

/**
 * Encapsula la información y métricas del error emitido por el servidor.
 *
 * @property code Código de identificación único del error (por ejemplo, "VALIDATION_ERROR" o "EMAIL_ALREADY_EXISTS").
 * @property message Mensaje descriptivo global del error destinado a ser mostrado o interpretado.
 * @property details Lista opcional de fallas específicas por campo ([ErrorDetailDto]) cuando ocurre un error de validación.
 * @property requestId Identificador único de la solicitud HTTP generado por el servidor para trazabilidad y auditoría de logs.
 */
@Serializable
data class ErrorPayload(
    val code: String? = null,
    val message: String? = null,
    val details: List<ErrorDetailDto>? = null,
    @SerialName("request_id") val requestId: String? = null,
)

/**
 * Representa el desglose individual de un error de validación sobre un parámetro o campo específico de la solicitud.
 *
 * @property loc Lista de cadenas que indican la ubicación exacta del campo afectado dentro del payload (por ejemplo, `["body", "email"]`).
 * @property message Descripción detallada del motivo por el cual el campo falló la validación.
 * @property type Identificador técnico de la regla de validación violada (por ejemplo, "value_error" o "string_too_short").
 */
@Serializable
data class ErrorDetailDto(
    val loc: List<String>? = null,
    val message: String? = null,
    val type: String? = null,
)