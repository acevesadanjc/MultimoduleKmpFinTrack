/*
 * ColorsLight.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.theme.colors

import androidx.compose.ui.graphics.Color
import com.kmp.core.design.theme.colors.custom.ColorBackgrounds
import com.kmp.core.design.theme.colors.custom.ColorStatus
import com.kmp.core.design.theme.colors.custom.ColorTexts

// --- Brand Colors ---
val primaryLight = Color(0xFF1A70ED)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFE6F4F4)
val onPrimaryContainerLight = Color(0xFF637070)
val secondaryLight = Color(0xFFA3A3A3)
val onSecondaryLight = Color(0xFFFFFFFF)
val tertiaryLight = Color(0xFFCB5856)
val onTertiaryLight = Color(0xFFFFFFFF)

// --- Structural Colors ---
val backgroundLight = Color(0xFFF4F4F4)
val onBackgroundLight = Color(0xFF1B1C1C)
val surfaceLight = Color(0xFFFBF9F8)
val onSurfaceLight = Color(0xFF1F242E) // color del valor del texto ingresado del text field
val surfaceVariantLight = Color(0xFFDFE3E3) // color del placeholder del text field
val onSurfaceVariantLight = Color(0xFFA1A1A1)

// --- State & Feedback ---
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF93000A)
val outlineLight = Color(0xFFDBE0E8)
val scrimLight = Color(0xFF000000)

val lightModeAppColors = AppColors(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    scrim = scrimLight,
    status = ColorStatus(
        error = Color(0xFFF75555),
        warning = Color(0xFFFACC15),
        info = Color(0xFF235DFF),
        success = Color(0xFF12D18E)
    ),
    text = ColorTexts(
        black = Color(0xFF1A1F29),
        white = Color(0xFFFFFFFF),
        gray = Color(0xFF7A8291),
        blue = Color(0xFF0F459E),
        fieldLabel = Color(0xFF1F242E),
        placeholder = Color(0xFFA1A1A1),
        link = Color(0xFF1470D1),
        positiveTrend = Color(0xFF17A36E),
        negativeTrend = Color(0xFFE5454D),
    ),
    backgrounds = ColorBackgrounds(
        black = Color(0xFF000000),
        white = Color(0xFFFFFFFF),
        yellow = Color(0xFFFFB700),
        blue = Color(0xFF0066FF),
        indicator = Color(0xFFE8F0FC),
        positiveTrend = Color(0xFFE5FAF2),
        negativeTrend = Color(0xFFFFEDF0),
    )
)
