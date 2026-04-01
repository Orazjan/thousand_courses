package com.atnzvdev.thousand.presentation.auth.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(
    onNavigateBack: () -> Unit,
    navigateToRegistration: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is LoginEvent.OnNavigateBack -> {}
                is LoginEvent.OnOKClicked -> {}
                is LoginEvent.OnVKClicked -> {}
            }
        }
    }
    Scaffold() { innerpadding ->
        LoginScreenContent(modifier = Modifier.padding(innerpadding), state = uiState)
    }
}

@Composable
fun LoginScreenContent(modifier: Modifier, state: LoginUiState) {

}
