package com.atnzvdev.thousand.presentation.auth.login

data class LoginUiState(
    val userEmail: String = "",
    val userPassword: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val isLoginEnabled: Boolean = false
)