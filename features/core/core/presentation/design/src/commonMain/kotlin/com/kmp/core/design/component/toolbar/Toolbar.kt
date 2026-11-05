/*
 * Toolbar.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.component.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.kmp.core.design.component.text.TextNormalBold
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.design.generated.resources.Res
import com.kmp.core.design.generated.resources.example
import com.kmp.core.design.generated.resources.ic_example
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    toolbarBackgroundColor: Color,
    height: Dp = Dimens.height64,
    iconSize: Dp = Dimens.height24,
    iconRightColor: Color,
    iconRight: DrawableResource? = null,
    titleTextColor: Color,
    titleText: String,
    onIconRightClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(color = toolbarBackgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        TextNormalBold(
            color = titleTextColor,
            text = titleText,
        )
        iconRight?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding4),
                contentAlignment = Alignment.CenterStart,
            ) {
                IconButton(onClick = onIconRightClick) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        tint = iconRightColor,
                        painter = painterResource(iconRight),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun ToolbarPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Dimens.padding16),
        verticalArrangement = Arrangement.spacedBy(Dimens.padding16),
    ) {
        Toolbar(
            toolbarBackgroundColor = Color.Black,
            iconRightColor = Color.White,
            iconRight = Res.drawable.ic_example,
            titleTextColor = Color.White,
            titleText = stringResource(Res.string.example),
        )
    }
}
