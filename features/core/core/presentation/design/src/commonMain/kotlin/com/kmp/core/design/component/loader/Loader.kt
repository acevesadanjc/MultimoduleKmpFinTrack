/*
 * Loader.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.component.loader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.design.enums.StatusLoading

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    currentState: StatusLoading = StatusLoading.DISMISS_LOADING,
) {
    if (currentState == StatusLoading.SHOW_LOADING) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = backgroundColor),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun LoadingPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Dimens.padding16),
        verticalArrangement = Arrangement.spacedBy(Dimens.padding16),
    ) {
        Loader(backgroundColor = Color.White)
    }
}
