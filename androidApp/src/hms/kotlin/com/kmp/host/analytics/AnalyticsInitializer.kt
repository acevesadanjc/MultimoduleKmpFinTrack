package com.kmp.host.analytics

import android.content.Context
import com.huawei.hms.analytics.HiAnalytics
import io.github.aakira.napier.Napier

internal object AnalyticsInitializer {
    fun initialize(context: Context) {
        try {
            HiAnalytics.getInstance(context).setAnalyticsEnabled(true)
        }   catch (error: Exception) {
            Napier.e(
                throwable = error,
                message = "Huawei Analytics initialization failed.",
                tag = "Analytics",
            )
        }
    }
}