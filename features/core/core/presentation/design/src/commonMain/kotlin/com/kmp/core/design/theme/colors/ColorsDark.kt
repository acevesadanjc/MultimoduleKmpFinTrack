/*
 * ColorsDark.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.theme.colors

import androidx.compose.ui.graphics.Color
import com.kmp.core.design.theme.colors.custom.ColorBackgrounds
import com.kmp.core.design.theme.colors.custom.ColorStatus
import com.kmp.core.design.theme.colors.custom.ColorTexts

// --- Brand Colors ---
val primaryDark = Color(0xFFB52217)
val onPrimaryDark = Color(0xFF263333)
val primaryContainerDark = Color(0xFFD7E5E5)
val onPrimaryContainerDark = Color(0xFF5A6767)
val secondaryDark = Color(0xFFA3A3A3)
val onSecondaryDark = Color(0xFF2C3131)
val tertiaryDark = Color(0xFFCB5856)
val onTertiaryDark = Color(0xFF302F3A)

// --- Structural Colors ---
val backgroundDark = Color(0xFF3C3C3C)
val onBackgroundDark = Color(0xFFE4E2E1)
val surfaceDark = Color(0xFF3C3C3C)
val onSurfaceDark = Color(0xFFE4E2E1)
val surfaceVariantDark = Color(0xFF424848)
val onSurfaceVariantDark = Color(0xFFC2C7C7)

// --- State & Feedback ---
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val outlineDark = Color(0xFF8C9291)
val scrimDark = Color(0xFF000000)

val darkModeAppColors = AppColors(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    scrim = scrimDark,
    status = ColorStatus(
        error = Color(0xFFF87171),
        warning = Color(0xFFFDE047),
        info = Color(0xFF60A5FA),
        success = Color(0xFF34D399)
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
