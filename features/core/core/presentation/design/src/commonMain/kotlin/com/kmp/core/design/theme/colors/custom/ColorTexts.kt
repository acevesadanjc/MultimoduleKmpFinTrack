package com.kmp.core.design.theme.colors.custom

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorTexts(
    val black: Color = Color.Unspecified,
    val white: Color = Color.Unspecified,
    val gray: Color = Color.Unspecified,
    val blue: Color = Color.Unspecified,
    val fieldLabel: Color = Color.Unspecified,
    val placeholder: Color = Color.Unspecified,
    val link: Color = Color.Unspecified,
    val positiveTrend: Color = Color.Unspecified,
    val negativeTrend: Color = Color.Unspecified
)