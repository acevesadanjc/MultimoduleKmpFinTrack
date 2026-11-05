package com.kmp.host.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import io.github.aakira.napier.Napier

internal object AnalyticsInitializer {
    fun initialize(context: Context) {
        try {
            FirebaseAnalytics.getInstance(context).setAnalyticsCollectionEnabled(true)
        }   catch (error: IllegalStateException) {
            Napier.e(
                throwable = error,
                message = "Firebase Analytics initialization failed.",
                tag = "Analytics",
            )
        }
    }
}