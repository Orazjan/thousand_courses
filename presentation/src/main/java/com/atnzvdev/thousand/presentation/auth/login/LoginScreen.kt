package com.atnzvdev.thousand.presentation.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.atnzvdev.thousand.presentation.components.PrimaryButton
import com.atnzvdev.thousand.presentation.components.PrimaryTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToRegistration: () -> Unit,
    onNavigateToResetPassword: () -> Unit,
    onOpenExternalAuth: (SocialProvider) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collectLatest { event ->
                when (event) {
                    is LoginEvent.NavigateToHome -> onNavigateToHome()
                    is LoginEvent.NavigateRegistration -> onNavigateToRegistration()
                    is LoginEvent.NavigateResetPassword -> onNavigateToResetPassword()
                    is LoginEvent.NavigateBack -> {
                        onNavigateToHome()
                    }

                    is LoginEvent.OpenExternalAuth -> onOpenExternalAuth(event.provider)

                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp), verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Вход",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            "Email",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        PrimaryTextField(
            value = state.userEmail,
            onValueChange = { viewModel.onAction(LoginAction.OnEmailChanged(it)) },
            placeholder = "example@gmail.com",
            isError = state.emailError != null,
            keyboardType = KeyboardType.Email
        )
        if (state.emailError != null) {
            Text(
                text = state.emailError!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Пароль",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        PrimaryTextField(
            value = state.userPassword,
            onValueChange = { viewModel.onAction(LoginAction.OnPasswordChanged(it)) },
            placeholder = "Введите пароль",
            isError = state.passwordError != null,
            keyboardType = KeyboardType.Password,
            isPassword = true
        )
        if (state.passwordError != null) {
            Text(
                text = state.passwordError!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.onAction(LoginAction.OnLoginClicked) },
            enabled = state.isLoginEnabled,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    "Вход",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Text("Нету аккаунта? ", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                text = "Регистрация",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { viewModel.onAction(LoginAction.OnRegisterClicked) })
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Забыл пароль",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { viewModel.onAction(LoginAction.OnForgotPasswordClicked) })

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PrimaryButton(
                text = "VK",
                backgroundColor = Color(0xFF2787F5),
                modifier = Modifier.weight(1f),
                onClick = { viewModel.onAction(LoginAction.OnVkClicked) })
            PrimaryButton(
                text = "OK",
                backgroundColor = Color(0xFFEE8208),
                modifier = Modifier.weight(1f),
                onClick = { viewModel.onAction(LoginAction.OnOkClicked) })
        }
    }
}