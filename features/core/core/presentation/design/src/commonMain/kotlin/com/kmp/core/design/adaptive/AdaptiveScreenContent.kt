package com.kmp.core.design.adaptive

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AdaptiveScreenContent(
    modifier: Modifier = Modifier,
    phoneContainer: @Composable () -> Unit,
    tabletContainer: @Composable () -> Unit,
){
    BoxWithConstraints(modifier = modifier){
        when(windowWidthSizeClass(maxWidth)){
            WindowWidthSizeClass.Compact,WindowWidthSizeClass.Medium -> phoneContainer()
            WindowWidthSizeClass.Expanded -> tabletContainer()
        }
    }
}