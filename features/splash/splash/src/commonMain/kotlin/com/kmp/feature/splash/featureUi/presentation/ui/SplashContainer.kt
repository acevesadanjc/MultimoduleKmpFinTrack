/*
 * SplashContainer.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.feature.splash.featureUi.presentation.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kmp.core.design.component.container.SafeScreenContainerTest
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.design.theme.AppTheme
import com.kmp.feature.splash.generated.resources.*
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashContainer(
    isLogoVisible: Boolean = false,
    isBrandingVisible: Boolean = false,
    onLogoVisibleChange: (Boolean) -> Unit = {},
    onBrandingVisibleChange: (Boolean) -> Unit = {},
) {

    // 2. Animación del Logo Principal (Scale + Bounce)
    val logoScale by animateFloatAsState(
        targetValue = if (isLogoVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    // 3. Animación del Branding (Alpha + Slide Up)
    val brandingAlpha by animateFloatAsState(
        targetValue = if (isBrandingVisible) 1f else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
    )

    // 4. Orquestación de la secuencia
    LaunchedEffect(Unit) {
        delay(100.milliseconds)
        onLogoVisibleChange(true)

        delay(500.milliseconds)
        onBrandingVisibleChange(true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.backgrounds.blue),
    ) {
        // Logo Central
        Image(
            painter = painterResource(Res.drawable.ic_kotlin),
            contentDescription = "Main Logo",
            modifier = Modifier
                .size(130.dp)
                .scale(logoScale)
                .align(Alignment.Center)
        )
        // Branding abajo con desvanecimiento (Alpha)
        Image(
            painter = painterResource(Res.drawable.ic_hitss),
            contentDescription = "Branding",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(100.dp)
                .padding(bottom = Dimens.padding4)
                .graphicsLayer {
                    alpha = brandingAlpha
                    translationY = (1f - brandingAlpha) * 20f
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashContainerPreview() {
    SafeScreenContainerTest {
        SplashContainer()
    }
}
