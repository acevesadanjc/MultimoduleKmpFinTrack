package com.kmp.core.tools.network.constants

object Network {

    object Client {
        // Base url
        const val BASE_URL = "https://fintrack-hitss.onrender.com"

        // Endpoint
        const val ENDPOINT_LOGIN = "/api/v1/auth/login"
        const val ENDPOINT_REGISTER = "/api/v1/auth/register"
        const val ENDPOINT_GET_ME = "/api/v1/users/me"
        const val ENDPOINT_DASHBOARD = "/api/v1/dashboard"
    }

    object Constants{
        const val REQUEST_TIMEOUT_MILLIS = 15000L
        const val CONNECT_TIMEOUT_MILLIS = 10000L
        const val SOCKET_TIMEOUT_MILLIS  = 10000L
        const val TAG_AND = "ktor_android_services:"
        const val TAG_IOS = "ktor_ios_services:"
    }

}