package com.kmp.feature.login.featureUi.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kmp.core.design.adaptive.AdaptiveScreenContent
import com.kmp.core.design.component.container.SafeScreenContainer
import com.kmp.core.design.component.container.SafeScreenContainerTest
import com.kmp.feature.login.featureUi.presentation.ui.phone.LoginPhoneContainer
import com.kmp.feature.login.featureUi.presentation.ui.tablet.LoginTabletContainer
import com.kmp.feature.login.featureUi.presentation.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel()
){
    SafeScreenContainer{
        AdaptiveScreenContent(
            phoneContainer  = { LoginPhoneContainer() },
            tabletContainer = { LoginTabletContainer() },
        )
    }
}

@Composable
private fun LoginAdaptivePreviewContent() {
    SafeScreenContainerTest {
        AdaptiveScreenContent(
            phoneContainer  = { LoginPhoneContainer() },
            tabletContainer = { LoginTabletContainer() },
        )
    }
}

@Preview(
    name = "Phone - Portrait",
    device = "spec:width=1080px,height=2340px,dpi=440",
    showSystemUi = true,
)
@Composable
private fun LoginPortraitPreview() {
    LoginAdaptivePreviewContent()
}

@Preview(
    name = "Phone - Landscape",
    device = "spec:width=2340px,height=1080px,dpi=440",
    showSystemUi = true,
)
@Composable
private fun LoginLandscapePreview() {
    LoginAdaptivePreviewContent()
}

@Preview(
    name = "Tablet 7 inch",
    device = "spec:width=1920px,height=1200px,dpi=320",
    showSystemUi = true,
)
@Composable
private fun LoginTabletAdaptivePreview() {
    LoginAdaptivePreviewContent()
}