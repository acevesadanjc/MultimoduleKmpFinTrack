/*
 * Dialog.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.component.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.infinum.arkive.annotations.ArkiveComposable
import com.kmp.core.design.component.button.ButtonPrimary
import com.kmp.core.design.component.text.TextNormalBold
import com.kmp.core.design.component.text.TextSmall
import com.kmp.core.design.generated.resources.Res
import com.kmp.core.design.generated.resources.accept
import com.kmp.core.design.generated.resources.cancel
import com.kmp.core.design.generated.resources.sub_tittle
import com.kmp.core.design.generated.resources.tittle
import com.kmp.core.design.model.dialog.DialogUiModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun InfoDialog(
    dialogUiModel: DialogUiModel,
    titleTextColor: Color,
    messageTextColor: Color,
    havePrimaryButton: Boolean = true,
    primaryButtonBackgroundColor: Color,
    primaryButtonTextColor: Color,
    haveSecondaryButton: Boolean = false,
    secondaryButtonBackgroundColor: Color = Color.Gray,
    secondaryButtonTextColor: Color = Color.White,
    onPrimaryButtonClick: () -> Unit = {},
    onSecondaryButtonClick: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            TextNormalBold(
                color = titleTextColor,
                text = dialogUiModel.title,
            )
        },
        text = {
            TextSmall(
                color = messageTextColor,
                text = dialogUiModel.message,
                textAlign = TextAlign.Start
            )
        },
        confirmButton = {
            if (havePrimaryButton) {
                ButtonPrimary(
                    backgroundColor = primaryButtonBackgroundColor,
                    textColors = primaryButtonTextColor,
                    text = dialogUiModel.primaryButtonText,
                    onClick = onPrimaryButtonClick,
                )
            }
        },
        dismissButton = {
            if (haveSecondaryButton) {
                ButtonPrimary(
                    backgroundColor = secondaryButtonBackgroundColor,
                    textColors = secondaryButtonTextColor,
                    text = dialogUiModel.secondaryButtonText,
                    onClick = onSecondaryButtonClick,
                )
            }
        },
    )
}

//TODO pasar el modelo
@Composable
fun ErrorDialog(
    dialogUiModel: DialogUiModel,
    titleTextColor: Color,
    descriptionTextColor: Color,
    primaryButtonBackgroundColor: Color,
    primaryButtonTextColor: Color,
    onPrimaryButtonClick: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            TextNormalBold(
                color = titleTextColor,
                text = dialogUiModel.title,
            )
        },
        text = {
            TextSmall(
                color = descriptionTextColor,
                text = dialogUiModel.message,
            )
        },
        confirmButton = {
            ButtonPrimary(
                backgroundColor = primaryButtonBackgroundColor,
                textColors = primaryButtonTextColor,
                text = dialogUiModel.primaryButtonText,
                onClick = onPrimaryButtonClick,
            )
        },
    )
}

@ArkiveComposable(
    name = "Dialog Custom",
    group = "Dialogs",
    tags = ["dialogs"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun DialogPreview() {
    Box(modifier = Modifier.size(width = 400.dp, height = 600.dp)) {
        InfoDialog(
            dialogUiModel = DialogUiModel(
                title = stringResource(Res.string.tittle),
                message = stringResource(Res.string.sub_tittle),
                primaryButtonText = stringResource(Res.string.accept),
                secondaryButtonText = stringResource(Res.string.cancel),
            ),
            haveSecondaryButton = true,
            titleTextColor = Color.Black,
            messageTextColor = Color.Black,
            primaryButtonBackgroundColor = Color.Black,
            primaryButtonTextColor = Color.White,
            secondaryButtonTextColor = Color.Black,
            secondaryButtonBackgroundColor = Color.LightGray,
        )
    }
}

@ArkiveComposable(
    name = "Dialog Error",
    group = "Dialogs",
    tags = ["dialogs"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun ErrorDialogPreview() {
    Box(modifier = Modifier.size(width = 400.dp, height = 600.dp)) {
        ErrorDialog(
            dialogUiModel = DialogUiModel(
                title = "Error Crítico",
                message = "Ha ocurrido un error inesperado al procesar la solicitud.",
                primaryButtonText = "Reintentar",
                secondaryButtonText = "",
            ),
            titleTextColor = Color.Red,
            descriptionTextColor = Color.Black,
            primaryButtonBackgroundColor = Color.Red,
            primaryButtonTextColor = Color.White,
        )
    }
}
