package com.kmp.core.design.model.dialog

data class DialogUiModel(
    val title: String = "",
    val message: String = "",
    val primaryButtonText: String = "Aceptar",
    val secondaryButtonText: String = "Cancelar",
)
