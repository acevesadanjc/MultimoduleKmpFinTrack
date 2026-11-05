/*
 * Button.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.infinum.arkive.annotations.ArkiveComposable
import com.kmp.core.design.component.text.TextNormalBold
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.design.generated.resources.Res
import com.kmp.core.design.generated.resources.ic_example
import com.kmp.core.design.generated.resources.ic_eye
import com.kmp.core.design.generated.resources.ic_test_1
import com.kmp.core.design.generated.resources.ic_test_2
import com.kmp.core.design.generated.resources.outlined_button
import com.kmp.core.design.generated.resources.primary_button
import com.kmp.core.design.generated.resources.primary_button_elevated
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = Dimens.corner48),
    height: Dp = Dimens.height40,
    enabled: Boolean = true,
    backgroundColor: Color,
    textColors: Color,
    text: String,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
    ) {
        TextNormalBold(
            modifier = Modifier.fillMaxWidth(),
            color = textColors,
            text = text,
        )
    }
}

@Composable
fun ButtonPrimaryElevated(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = Dimens.corner48),
    height: Dp = Dimens.height40,
    enabled: Boolean = true,
    backgroundColor: Color,
    textColors: Color,
    text: String,
    onClick: () -> Unit = {},
) {
    ElevatedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = Dimens.elevation12
        ),
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
    ) {
        TextNormalBold(
            modifier = Modifier.fillMaxWidth(),
            color = textColors,
            text = text,
        )
    }
}

@Composable
fun ButtonOutlined(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = Dimens.corner48),
    height: Dp = Dimens.height40,
    enabled: Boolean = true,
    backgroundColor: Color,
    borderColor: Color,
    borderWidth: Dp = Dimens.border2,
    textColors: Color,
    text: String,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = Color.Transparent,
        ),
        border = BorderStroke(width = borderWidth, color = borderColor),
    ) {
        TextNormalBold(
            modifier = Modifier.fillMaxWidth(),
            color = textColors,
            text = text,
        )
    }
}

@Composable
fun CircularIconButton(
    modifier: Modifier = Modifier,
    size: Dp = Dimens.height24,
    backgroundColor: Color,
    iconColor: Color,
    icon: DrawableResource,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color = backgroundColor)
            .minimumInteractiveComponentSize()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(size),
            painter = painterResource(resource = icon),
            tint = iconColor,
            contentDescription = null,
        )
    }
}

@ArkiveComposable(
    name = "Primary Button",
    group = "Buttons",
    tags = ["cta"],
    designNodeId = "2001-8",
)
@Preview(showBackground = true)
@Composable
fun PreviewButtonPrimary() {
    Box(modifier = Modifier.padding(Dimens.padding16)) {
        ButtonPrimary(
            backgroundColor = Color.Black,
            textColors = Color.White,
            text = stringResource(Res.string.primary_button),
        )
    }
}

@ArkiveComposable(
    name = "Primary Button Elevated",
    group = "Buttons",
    tags = ["cta"],
    designNodeId = "2001-8",
)
@Preview(showBackground = true)
@Composable
fun PreviewButtonPrimaryElevated() {
    Box(modifier = Modifier.padding(Dimens.padding16)) {
        ButtonPrimaryElevated(
            backgroundColor = Color.Black,
            textColors = Color.White,
            text = stringResource(Res.string.primary_button_elevated),
        )
    }
}

@ArkiveComposable(
    name = "Outlined Button",
    group = "Buttons",
    tags = ["outlined"],
    designNodeId = "2001-8",
)
@Preview(showBackground = true)
@Composable
fun PreviewButtonOutlined() {
    Box(modifier = Modifier.padding(Dimens.padding16)) {
        ButtonOutlined(
            backgroundColor = Color.White,
            borderColor = Color.Black,
            textColors = Color.Black,
            text = stringResource(Res.string.outlined_button),
        )
    }
}

@ArkiveComposable(
    name = "Circular Button",
    group = "Buttons",
    tags = ["cta"],
    designNodeId = "2001-8",
)
@Preview(showBackground = true)
@Composable
fun PreviewCircularIconButton() {
    Box(modifier = Modifier.padding(Dimens.padding16)) {
        CircularIconButton(
            backgroundColor = Color.Black,
            iconColor = Color.White,
            icon = Res.drawable.ic_example,
        )
    }
}
