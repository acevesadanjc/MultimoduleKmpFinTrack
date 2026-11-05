/*
 * Text.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.component.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.infinum.arkive.annotations.ArkiveComposable
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.design.generated.resources.Res
import com.kmp.core.design.generated.resources.example
import org.jetbrains.compose.resources.stringResource

/**
 * Standardized typography system for the application.
 *
 * Usage guide:
 *
 * - `TextSmallExtra` (12px)
 *
 * - `TextSmall` (14px):
 *
 * - `TextNormal` (16px)
 *
 * - `TextMedium` (20px)
 *
 * - `TextBig` (24px)
 */

@Composable
fun TextSmallExtra(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.textSizeExtraSmall,
    color: Color,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
        ),
        color = color,
    )
}

@Composable
fun TextSmallExtraBold(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.textSizeExtraSmall,
    color: Color,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
        ),
        color = color,
    )
}

@Composable
fun TextSmall(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.textSizeSmall,
    color: Color,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
        ),
        color = color,
    )
}

@Composable
fun TextSmallBold(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.textSizeSmall,
    color: Color,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
        ),
        color = color,
    )
}

@Composable
fun TextNormal(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.textSizeNormal,
    color: Color,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
        ),
        color = color,
    )
}

@Composable
fun TextNormalBold(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.textSizeNormal,
    color: Color,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
        ),
        color = color,
    )
}

@Composable
fun TextMedium(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.textSizeMedium,
    color: Color,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
        ),
        color = color,
    )
}

@Composable
fun TextMediumBold(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.textSizeMedium,
    color: Color,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
        ),
        color = color,
    )
}

@Composable
fun TextBig(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.textSizeBig,
    color: Color,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
        ),
        color = color,
    )
}

@Composable
fun TextBigBold(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.textSizeBig,
    color: Color,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
        ),
        color = color,
    )
}

@Composable
fun TextUrlLink(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.textSizeNormal,
    textColor: Color,
    linkColor: Color,
    text: String,
    linkText: String,
    url: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            pushStyle(
                SpanStyle(
                    color = textColor,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Normal,
                )
            )
            append(text)
            withLink(
                LinkAnnotation.Url(
                    url = url,
                    styles = androidx.compose.ui.text.TextLinkStyles(
                        style = SpanStyle(
                            color = linkColor,
                            fontSize = fontSize,
                            fontWeight = FontWeight.Bold,
                        ),
                    ),
                )
            ) {
                append(linkText)
            }
        },
        style = MaterialTheme.typography.bodySmall.copy(textAlign = textAlign),
    )
}

@ArkiveComposable(
    name = "Text Small Extra",
    group = "Texts",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun TextSmallExtraPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        TextSmallExtra(
            color = Color.Black,
            text = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Text Small Extra Bold",
    group = "Texts",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun TextSmallExtraBoldPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        TextSmallExtraBold(
            color = Color.Black,
            text = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Text Small",
    group = "Texts",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun TextSmallPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        TextSmall(
            color = Color.Black,
            text = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Text Small Bold",
    group = "Texts",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun TextSmallBoldPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        TextSmallBold(
            color = Color.Black,
            text = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Text Normal",
    group = "Texts",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun TextNormalPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        TextNormal(
            color = Color.Black,
            text = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Text Normal Bold",
    group = "Texts",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun TextNormalBoldPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        TextNormalBold(
            color = Color.Black,
            text = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Text Medium",
    group = "Texts",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun TextMediumPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        TextMedium(
            color = Color.Black,
            text = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Text Medium Bold",
    group = "Texts",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun TextMediumBoldPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        TextMediumBold(
            color = Color.Black,
            text = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Text Big",
    group = "Texts",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun TextBigPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        TextBig(
            color = Color.Black,
            text = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Text Big Bold",
    group = "Texts",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun TextBigBoldPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        TextBigBold(
            color = Color.Black,
            text = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Text Url Link",
    group = "Texts",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun TextUrlLinkPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        TextUrlLink(
            textColor = Color.Black,
            linkColor = Color.Blue,
            text = stringResource(Res.string.example),
            linkText = stringResource(Res.string.example),
            url = stringResource(Res.string.example),
        )
    }
}
