package com.atnzvdev.thousand.presentation.auth.login

sealed interface LoginAction {
    data class OnEmailChanged(val email: String) : LoginAction
    data class OnPasswordChanged(val password: String) : LoginAction

    data object OnLoginClicked : LoginAction
    data object OnBackClicked : LoginAction
    data object OnForgotPasswordClicked : LoginAction
    data object OnRegisterClicked : LoginAction
    data object OnVkClicked : LoginAction
    data object OnOkClicked : LoginAction
}