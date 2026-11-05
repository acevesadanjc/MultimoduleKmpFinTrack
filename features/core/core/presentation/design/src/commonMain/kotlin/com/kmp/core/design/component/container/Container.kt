/*
 * Container.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.component.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.design.theme.AppTheme
import com.kmp.core.design.theme.SystemAppearance

@Composable
fun SafeScreenContainer(
    modifier: Modifier = Modifier,
    systemColor: Color = AppTheme.colors.primary,
    backgroundColor: Color = AppTheme.colors.background,
    isSystemIconsDark: Boolean? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    isSystemIconsDark?.let {
        SystemAppearance(isDarkMode = isSystemIconsDark)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = systemColor,
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .background(color = backgroundColor),
        ) {
            content()
        }
    }
}

@Composable
fun SafeScreenContainerTest(
    modifier: Modifier = Modifier,
    systemColor: Color = AppTheme.colors.primary,
    backgroundColor: Color = AppTheme.colors.background,
    content: @Composable BoxScope.() -> Unit,
) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = systemColor,
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .background(color = backgroundColor),
            ) {
                content()
            }
        }
    }
}

@Composable
fun SafeScreenSectionContainerPreview(
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppTheme.colors.background,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .padding(all = Dimens.padding16)
            .background(color = backgroundColor),
    ) {
        content()
    }
}
