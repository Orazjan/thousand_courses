package com.atnzvdev.thousand.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<LoginEvent>()
    val event = _event.receiveAsFlow()


    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnEmailChanged -> emailChanged(action.email)
            is LoginAction.OnPasswordChanged -> passwordChanged(action.password)

            LoginAction.OnLoginClicked -> loginClicked()
            LoginAction.OnBackClicked -> navigateBack()
            LoginAction.OnForgotPasswordClicked -> navigateResetPassword()
            LoginAction.OnRegisterClicked -> navigateRegistration()
            LoginAction.OnOkClicked -> openSocialAuth(SocialProvider.OK)
            LoginAction.OnVkClicked -> openSocialAuth(SocialProvider.VK)
        }
    }

    private fun emailChanged(email: String) {
        if (email.contains("[а-яА-Я]".toRegex())) return
        val trimEmail = email.trim()
        _uiState.update { it.copy(userEmail = trimEmail, emailError = null) }
        updateLoginEnabled()
    }

    private fun updateLoginEnabled() {
        val state = _uiState.value

        val isEmailValid = state.userEmail.isNotBlank()
        val isPasswordValid = state.userPassword.isNotBlank()

        _uiState.value = state.copy(
            isLoginEnabled = isEmailValid && isPasswordValid && !state.isLoading
        )
    }

    private fun loginClicked() {
        val state = _uiState.value

        if (state.isLoading) return

        val emailError = validateEmail(state.userEmail)
        val passwordError = validatePassword(state.userPassword)

        if (emailError != null || passwordError != null) {
            _uiState.value = state.copy(
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        performFakeLogin()
    }

    private fun performFakeLogin() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        updateLoginEnabled()

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = false)
            updateLoginEnabled()
            _event.send(LoginEvent.NavigateToHome)
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _event.send(LoginEvent.NavigateBack)
        }
    }

    private fun navigateResetPassword() {
        viewModelScope.launch {
            _event.send(LoginEvent.NavigateResetPassword)
        }
    }

    private fun navigateRegistration() {
        viewModelScope.launch {
            _event.send(LoginEvent.NavigateRegistration)
        }
    }

    private fun openSocialAuth(provider: SocialProvider) {
        viewModelScope.launch {
            _event.send(LoginEvent.OpenExternalAuth(provider))
        }
    }


    private fun passwordChanged(password: String) {

        _uiState.value = _uiState.value.copy(userPassword = password, passwordError = null)
        updateLoginEnabled()
    }

    private fun validateEmail(email: String): String? {
        val strictEmailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$".toRegex()
        return when {
            email.isBlank() -> "Введите email"
            !strictEmailRegex.matches(email) -> "Некорректный формат (нужно: текст@текст.текст)"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Введите пароль"
            password.length < 6 -> "Минимум 6 символов"
            else -> null
        }
    }

}