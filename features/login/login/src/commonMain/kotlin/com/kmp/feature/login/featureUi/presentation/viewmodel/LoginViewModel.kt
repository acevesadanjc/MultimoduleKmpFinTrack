/*
 * LoginViewModel.kt.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.feature.login.featureUi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmp.core.design.enums.StatusLoading
import com.kmp.core.design.model.dialog.DialogUiModel
import com.kmp.core.domain.usecase.login.LoginNetworkUseCase
import com.kmp.core.domain.usecase.user.UserDataStoreUseCase
import com.kmp.core.tools.network.api.NetworkResult
import com.kmp.core.tools.network.constants.Network
import com.kmp.feature.login.featureLogic.usecase.LoginValidationUseCase
import com.kmp.feature.login.generated.resources.Res
import com.kmp.feature.login.generated.resources.accept
import com.kmp.feature.login.generated.resources.error
import com.kmp.feature.login.generated.resources.please_try_again_later
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.annotation.KoinViewModel


@KoinViewModel
class LoginViewModel(
    private val networkUseCase: LoginNetworkUseCase,
    private val networkDataStoreUseCase: UserDataStoreUseCase,
    private val validateLoginUseCase: LoginValidationUseCase
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun initStates() {
        _loginUiState.update { state ->
            state.copy(
                email = "test011@gmail.com",
                password = "Password123*",
            )
        }
    }

    fun navigationHandled() = viewModelScope.launch {
        _loginUiState.update { state -> state.copy(navigationTarget = null) }
    }

    fun updateEmail(email: String) = viewModelScope.launch {
        _loginUiState.update { state -> state.copy(email = email) }
    }

    fun updatePassword(password: String) = viewModelScope.launch {
        _loginUiState.update { state -> state.copy(password = password) }
    }

    fun navigateToRegister() = viewModelScope.launch {
        _loginUiState.update { state -> state.copy(navigationTarget = LoginNavigationTargetUiState.Register) }
    }

    fun validateFields() {
        viewModelScope.launch {
            val validateFields = validateLoginUseCase.invoke(
                password = loginUiState.value.password
            )
            if (validateFields.hasError) {
                val errorMessage = validateFields.message?.let { getString(it) }.orEmpty()
                _loginUiState.update { state ->
                    state.copy(
                        errorDialog = showErrorDialog(
                            errorMessage
                        )
                    )
                }
            } else {
                login()
            }
        }
    }

    private fun login() = viewModelScope.launch {
        networkUseCase.login(
            email = loginUiState.value.email,
            password = loginUiState.value.password,
            url = Network.Client.ENDPOINT_LOGIN
        ).onStart {
            _loginUiState.update { state -> state.copy(isLoading = StatusLoading.SHOW_LOADING) }
        }.catch {
            _loginUiState.update { state ->
                state.copy(
                    isLoading = StatusLoading.DISMISS_LOADING,
                    errorDialog = showErrorDialog(),
                )
            }
        }.collect { result ->
            when(result) {
                is NetworkResult.Success -> {
                    saveAccessToken(accessToken = result.response.accessToken)
                }
                is NetworkResult.Error -> {
                    _loginUiState.update { state ->
                        state.copy(
                            isLoading = StatusLoading.DISMISS_LOADING,
                            errorDialog = showErrorDialog(result.message),
                        )
                    }
                }
            }
        }
    }

    private fun saveAccessToken(accessToken: String) = viewModelScope.launch {
        networkDataStoreUseCase.saveUserToken(token = accessToken).catch {
            _loginUiState.update { state ->
                state.copy(
                    isLoading = StatusLoading.DISMISS_LOADING,
                    errorDialog = showErrorDialog(),
                )
            }
        }.collect {
            _loginUiState.update { state -> state.copy(isLoading = StatusLoading.DISMISS_LOADING) }
            _loginUiState.update { state -> state.copy(navigationTarget = LoginNavigationTargetUiState.Home) }
        }
    }

    private suspend fun showErrorDialog(message: String? = null) = DialogUiModel(
        title = getString(resource = Res.string.error),
        message = message ?: getString(resource = Res.string.please_try_again_later),
        primaryButtonText = getString(resource = Res.string.accept)
    )

    fun dismissDialog() = viewModelScope.launch {
        _loginUiState.update { state -> state.copy(errorDialog = null) }
    }
}
