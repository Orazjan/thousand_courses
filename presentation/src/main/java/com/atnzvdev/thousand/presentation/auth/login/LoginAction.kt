package com.atnzvdev.thousand.presentation.auth.login

sealed interface LoginAction {
    data class OnEmailChanged(val email: String) : LoginAction
    data class OnPasswordChanged(val password: String) : LoginAction
    data object OnLoginClicked : LoginAction
}
