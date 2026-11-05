package com.kmp.core.tools.compose.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> StateFlow<T>.CollectWithLifecycleLaunchEffect(
    vararg keys: Any?,
    action: suspend (T) -> Unit,
) {
    LaunchedEffect(this, *keys) {
        this@CollectWithLifecycleLaunchEffect.collectLatest { value ->
            action(value)
        }
    }
}