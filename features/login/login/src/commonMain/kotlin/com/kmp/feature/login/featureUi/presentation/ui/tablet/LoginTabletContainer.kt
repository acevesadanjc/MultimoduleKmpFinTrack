package com.kmp.feature.login.featureUi.presentation.ui.tablet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kmp.core.design.component.container.SafeScreenContainerTest
import com.kmp.core.design.component.text.TextBigBold
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.design.theme.AppTheme
import com.kmp.feature.login.generated.resources.Res
import com.kmp.feature.login.generated.resources.example_tablet
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginTabletContainer() {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.padding16)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(
            space = Dimens.padding16,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextBigBold(
            modifier = Modifier.fillMaxWidth(),
            color = AppTheme.colors.text.black,
            text = stringResource(Res.string.example_tablet),
        )
    }
}

@Preview(
    name = "Phone Device - Landscape",
    device = "spec:width=2340px,height=1080px,dpi=440",
    showSystemUi = true,
)
@Composable
private fun LoginLandscapePreview() {
    SafeScreenContainerTest {
        LoginTabletContainer()
    }
}

@Preview(
    name = "Tablet 7 pulgadas",
    device = "spec:width=1920px,height=1200px,dpi=320",
    showSystemUi = true,
)
@Composable
private fun LoginTabletAdaptivePreview() {
    SafeScreenContainerTest {
        LoginTabletContainer()
    }
}