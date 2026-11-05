/*
 * PullToRefresh.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.component.pull_to_refresh

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.design.enums.StatusLoading

@Composable
fun PullToRefresh(
    modifier: Modifier = Modifier,
    currentState: StatusLoading = StatusLoading.DISMISS_LOADING,
    onRefresh: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit = {},
) {
    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = getStatusLoading(currentState = currentState),
        onRefresh = onRefresh,
    ) {
        content()
    }
}

private fun getStatusLoading(currentState: StatusLoading): Boolean =
    currentState == StatusLoading.SHOW_LOADING

@Preview(
    showBackground = true,
)
@Composable
private fun PullToRefreshPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Dimens.padding16),
        verticalArrangement = Arrangement.spacedBy(Dimens.padding16),
    ) {
        PullToRefresh()
    }
}
