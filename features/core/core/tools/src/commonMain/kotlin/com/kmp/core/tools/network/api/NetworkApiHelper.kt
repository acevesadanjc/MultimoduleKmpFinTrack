/*
 * NetworkApiHelper.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.tools.network.api

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.io.IOException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified Response : BaseResponse, Domain> safeApiCall(
    crossinline apiCall: suspend () -> HttpResponse,
    crossinline transform: (Response) -> Domain
): NetworkResult<Domain> =
    try {
        val response = apiCall()

        if (response.status.isSuccess()) {
            val body = response.body<Response>()
            if (body.isSuccessful) {
                NetworkResult.Success(response = transform(body))
            } else {
                NetworkResult.Error(
                    message = body.message?.takeIf { message -> message.isNotBlank() } ?: "Error unknown",
                    errorType = NetworkErrorType.UNKNOWN,
                )
            }
        } else {
            val statusCode = response.status.value
            val errorMessage = parseErrorMessage(response) ?: "Error cliente ($statusCode)"
            NetworkResult.Error(
                message = errorMessage,
                code = statusCode,
                errorType = NetworkErrorType.HTTP,
            )
        }
    } catch (exception: Exception) {
        /**
         * Ktor viene configurado con expectSuccess = true. Significa que si el servidor responde con un código $4xx$ o $5xx$,
         * Ktor lanza una excepción automáticamente (ClientRequestException o ServerResponseException)
         * */
        // Permitir que la cancelación de corrutinas fluya
        if (exception is CancellationException && exception !is TimeoutCancellationException) {
            throw exception
        }

        exception.printStackTrace()

        when (exception) {
            is TimeoutCancellationException -> NetworkResult.Error(
                message = "Error timeout: ${exception.message}",
                errorType = NetworkErrorType.TIMEOUT,
            )
            is IOException -> NetworkResult.Error(
                message = "Error network: ${exception.message}",
                errorType = NetworkErrorType.NETWORK,
            )
            is ClientRequestException -> {
                val statusCode = exception.response.status.value
                val errorMessage = parseErrorMessage(exception.response) ?: "Error cliente ($statusCode)"

                NetworkResult.Error(
                    message = errorMessage,
                    code = statusCode,
                    errorType = NetworkErrorType.HTTP,
                )
            }
            is ServerResponseException -> {
                val statusCode = exception.response.status.value
                val errorMessage = parseErrorMessage(exception.response) ?: "Error servidor ($statusCode)"

                NetworkResult.Error(
                    message = errorMessage,
                    code = statusCode,
                    errorType = NetworkErrorType.HTTP,
                )
            }
            else -> NetworkResult.Error(
                message = "Error unknown: ${exception.message}",
                errorType = NetworkErrorType.UNKNOWN,
            )
        }
    }

/**
 * Función auxiliar para deserializar el JSON de error emitido por el servidor
 */
suspend inline fun parseErrorMessage(response: HttpResponse): String? {
    return runCatching {
        val errorBody = response.body<ApiErrorResponse>()
        val mainMessage = errorBody.error?.message
        val details = errorBody.error?.details

        if (!details.isNullOrEmpty()) {
            // Formatea cada campo con error
            val formattedDetails = details.joinToString(separator = "\n") { detail ->
                val fieldName = detail.loc?.lastOrNull() ?: "Campo"
                "- $fieldName: ${detail.message}"
            }
            "$mainMessage\n$formattedDetails"
        } else {
            mainMessage
        }
    }.getOrNull()
}