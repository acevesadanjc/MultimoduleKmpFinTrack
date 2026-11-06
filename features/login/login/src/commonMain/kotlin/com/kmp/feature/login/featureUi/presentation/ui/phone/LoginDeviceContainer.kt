package com.kmp.feature.login.featureUi.presentation.ui.phone

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.kmp.core.design.component.button.ButtonPrimaryElevated
import com.kmp.core.design.component.container.SafeScreenContainerTest
import com.kmp.core.design.component.field.TextFieldPassword
import com.kmp.core.design.component.field.TextFieldSimple
import com.kmp.core.design.component.text.ClickableText
import com.kmp.core.design.component.text.TextBigBold
import com.kmp.core.design.component.text.TextNormal
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.design.theme.AppTheme
import com.kmp.feature.login.featureUi.presentation.viewmodel.LoginUiState
import com.kmp.feature.login.generated.resources.Res
import com.kmp.feature.login.generated.resources.ic_email
import com.kmp.feature.login.generated.resources.ic_password
import com.kmp.feature.login.generated.resources.ic_visibility_off
import com.kmp.feature.login.generated.resources.ic_visibility_on
import com.kmp.feature.login.generated.resources.il_finance
import com.kmp.feature.login.generated.resources.login_create_account
import com.kmp.feature.login.generated.resources.login_description
import com.kmp.feature.login.generated.resources.login_field_email
import com.kmp.feature.login.generated.resources.login_field_password
import com.kmp.feature.login.generated.resources.login_login
import com.kmp.feature.login.generated.resources.login_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginPhoneContainer(
    state: LoginUiState,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.padding24)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(
            space = Dimens.paddingNome,
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
    ) {

        Spacer(modifier = Modifier.height(Dimens.height32))

        TextBigBold(
            modifier = Modifier.fillMaxWidth(),
            color = AppTheme.colors.text.blue,
            text = stringResource(Res.string.login_title),
            textAlign = TextAlign.Left
        )

        Spacer(modifier = Modifier.height(Dimens.height8))

        TextNormal(
            modifier = Modifier.fillMaxWidth(),
            color = AppTheme.colors.text.gray,
            text = stringResource(Res.string.login_description),
            textAlign = TextAlign.Left
        )

        Spacer(modifier = Modifier.height(Dimens.height28))

        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(Res.drawable.il_finance),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(Dimens.height32))

        TextFieldSimple(
            value = state.email,
            onValueChange = onEmailChange,
            labelColor = AppTheme.colors.text.black,
            label = stringResource(Res.string.login_field_email),
            placeholderColor = AppTheme.colors.text.black,
            placeholder = stringResource(Res.string.login_field_email),
            leadingIcon = Res.drawable.ic_email,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        )

        Spacer(modifier = Modifier.height(Dimens.height16))

        TextFieldPassword(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            onValueChange = onPasswordChange,
            labelColor = AppTheme.colors.text.fieldLabel,
            label = stringResource(Res.string.login_field_password),
            labelTextAlign = TextAlign.Start,
            placeholderColor = AppTheme.colors.text.placeholder,
            placeholder = stringResource(Res.string.login_field_password),
            placeholderTextAlign = TextAlign.Start,
            leadingIcon = Res.drawable.ic_password, // DrawableResource
            trailingIconActive = Res.drawable.ic_visibility_on,
            trailingIconInActive = Res.drawable.ic_visibility_off,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onAny = {
                    focusManager.clearFocus()
                }
            ),
        )

        Spacer(modifier = Modifier.height(Dimens.height24))

        ButtonPrimaryElevated(
            modifier = Modifier.fillMaxWidth(),
            height = Dimens.height52,
            backgroundColor = AppTheme.colors.primary,
            textColors = AppTheme.colors.text.white,
            text = stringResource(Res.string.login_login),
            onClick = {
                focusManager.clearFocus()
                onLoginClick()
            }
        )
        Spacer(modifier = Modifier.height(Dimens.height16))

        ClickableText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.padding8, bottom = Dimens.height16),
            fontSize = Dimens.textSizeSmall,
            color = AppTheme.colors.primary,
            text = stringResource(Res.string.login_create_account),
            onClick = onRegisterClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPhoneContainerPreview() {
    SafeScreenContainerTest {
        LoginPhoneContainer(state = LoginUiState())
    }
}