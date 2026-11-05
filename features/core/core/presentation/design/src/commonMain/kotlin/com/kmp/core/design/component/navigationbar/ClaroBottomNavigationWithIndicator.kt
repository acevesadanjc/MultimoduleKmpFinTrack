/*
 * ClaroBottomNavigationWithIndicator.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.component.navigationbar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kmp.core.design.component.text.TextSmallExtraBold
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.tools.animation.Animation
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ClaroBottomNavigationWithIndicator(
    modifier: Modifier = Modifier,
    lineColor: Color,
    bottomNavigationColor: Color,
    tabs: List<Triple<String, DrawableResource, Boolean>>,
    selectedTabIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {},
) {

    var rowWidthPx by remember { mutableIntStateOf(value = 0) }
    val tabWidthDp = if (rowWidthPx > 0) with(LocalDensity.current) { (rowWidthPx / tabs.size).toDp() } else 0.dp

    val indicatorWidth = (tabWidthDp - Dimens.width32).coerceAtLeast(Dimens.widthNome)
    val extraOffset = Dimens.width16

    val indicatorOffsetPx by animateDpAsState(
        targetValue = (tabWidthDp * selectedTabIndex) + extraOffset,
        animationSpec = tween(durationMillis = Animation.DEFAULT_ANIMATION_DURATION),
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = bottomNavigationColor,
        shape = RoundedCornerShape(topStart = Dimens.border16, topEnd = Dimens.border16),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    rowWidthPx = coordinates.size.width
                },
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                tabs.forEachIndexed { index, tab ->
                    val isSelected = selectedTabIndex == index
                    val alpha = if (isSelected) 1f else 0.7f
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                interactionSource = null,
                                indication = null,
                            ) { onTabSelected(index) }
                            .padding(vertical = Dimens.padding12),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.padding(bottom = Dimens.padding2),
                            painter = painterResource(tab.second),
                            tint = Color.White.copy(alpha = alpha),
                            contentDescription = tab.first,
                        )
                        TextSmallExtraBold(
                            color = Color.White.copy(alpha = alpha),
                            text = tab.first,
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = Dimens.height4),
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = indicatorOffsetPx)
                        .width(indicatorWidth)
                        .height(height = Dimens.height4)
                        .background(
                            color = lineColor,
                            shape = RoundedCornerShape(size = Dimens.border48),
                        ),
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun ClaroBottomNavigationPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Dimens.padding16),
        verticalArrangement = Arrangement.spacedBy(Dimens.padding16),
    ) {
        ClaroBottomNavigationWithIndicator(
            lineColor = Color.White,
            bottomNavigationColor = Color.Black,
            tabs = emptyList(),
            selectedTabIndex = 1,
        )
    }
}
