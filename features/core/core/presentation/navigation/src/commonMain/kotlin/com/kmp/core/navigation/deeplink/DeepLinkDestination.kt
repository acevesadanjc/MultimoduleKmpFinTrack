package com.kmp.core.navigation.deeplink

import com.kmp.core.navigation.enum.DeepLinkDestination

internal object DeepLinkParser {
    fun parse(url:String): DeepLinkDestination? =
        if(url == "hostapp://login"){
            DeepLinkDestination.Login
        } else {
            null
        }
}