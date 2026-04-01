package com.atnzvdev.thousand.presentation.auth.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()


    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnLoginClicked -> loginClicked()
            is LoginAction.OnEmailChanged -> emailChanged(action.email)
            is LoginAction.OnPasswordChanged -> passwordChanged(action.password)
        }
    }

    private fun emailChanged(email: String) {
        TODO("Not yet implemented")
    }

    private fun passwordChanged(password: String) {
        TODO("Not yet implemented")
    }

    fun loginClicked() {
        TODO()
    }

}