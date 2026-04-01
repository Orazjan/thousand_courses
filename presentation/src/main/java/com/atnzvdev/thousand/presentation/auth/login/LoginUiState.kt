package com.atnzvdev.thousand.presentation.auth.login

data class LoginUiState(
    val userEmail: String = "",
    val userPassword: String = "",
    val isLoading: Boolean = false
)