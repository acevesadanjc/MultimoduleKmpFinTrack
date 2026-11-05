/*
 * Card.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.component.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.infinum.arkive.annotations.ArkiveComposable
import com.kmp.core.design.component.text.TextNormalBold
import com.kmp.core.design.component.text.TextSmall
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.design.generated.resources.Res
import com.kmp.core.design.generated.resources.example
import org.jetbrains.compose.resources.stringResource

@Composable
fun SimpleCard(
    modifier: Modifier = Modifier,
    titleTextColor: Color,
    titleText: String,
    subtitleTextColor: Color,
    subtitleText: String,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(all = Dimens.padding16)) {
            TextNormalBold(
                modifier = Modifier.fillMaxWidth(),
                color = titleTextColor,
                text = titleText,
            )
            Spacer(modifier = Modifier.height(Dimens.height16))
            TextSmall(
                modifier = Modifier.fillMaxWidth(),
                color = subtitleTextColor,
                text = subtitleText,
            )
        }
    }
}

@Composable
fun ElevateCard(
    modifier: Modifier = Modifier,
    titleTextColor: Color,
    titleText: String,
    subtitleTextColor: Color,
    subtitleText: String,
    onClick: () -> Unit = {},
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.elevation12),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(all = Dimens.padding16)) {
            TextNormalBold(
                modifier = Modifier.fillMaxWidth(),
                color = titleTextColor,
                text = titleText,
            )
            Spacer(modifier = Modifier.height(Dimens.height16))
            TextSmall(
                modifier = Modifier.fillMaxWidth(),
                color = subtitleTextColor,
                text = subtitleText,
            )
        }
    }
}

@Composable
fun OutlineCard(
    modifier: Modifier = Modifier,
    titleTextColor: Color,
    titleText: String,
    subtitleTextColor: Color,
    subtitleText: String,
    onClick: () -> Unit = {},
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(all = Dimens.padding16)) {
            TextNormalBold(
                modifier = Modifier.fillMaxWidth(),
                color = titleTextColor,
                text = titleText,
            )
            Spacer(modifier = Modifier.height(Dimens.height16))
            TextSmall(
                modifier = Modifier.fillMaxWidth(),
                color = subtitleTextColor,
                text = subtitleText,
            )
        }
    }
}

@ArkiveComposable(
    name = "Simple Card",
    group = "Cards",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun SimpleCardCustomPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ) {
        SimpleCard(
            titleTextColor = Color.Black,
            titleText = stringResource(Res.string.example),
            subtitleTextColor = Color.Black,
            subtitleText = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Elevate Card",
    group = "Cards",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun ElevateCardCustomPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ) {
        ElevateCard(
            titleTextColor = Color.Black,
            titleText = stringResource(Res.string.example),
            subtitleTextColor = Color.Black,
            subtitleText = stringResource(Res.string.example),
        )
    }
}

@ArkiveComposable(
    name = "Outlined Card",
    group = "Cards",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun OutlinedCardCustomPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ) {
        OutlineCard(
            titleTextColor = Color.Black,
            titleText = stringResource(Res.string.example),
            subtitleTextColor = Color.Black,
            subtitleText = stringResource(Res.string.example),
        )
    }
}
