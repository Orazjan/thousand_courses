package com.atnzvdev.thousand.presentation.auth.login

sealed interface LoginEvent {
    data object NavigateBack : LoginEvent
    data object NavigateResetPassword : LoginEvent
    data object NavigateRegistration : LoginEvent
    data object NavigateToHome : LoginEvent
    data class OpenExternalAuth(val provider: SocialProvider) : LoginEvent
}

enum class SocialProvider {
    VK, OK
}