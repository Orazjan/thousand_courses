package com.atnzvdev.thousand.presentation.auth.login

sealed interface LoginEvent {
    data object OnNavigateBack : LoginEvent
    data object OnVKClicked : LoginEvent
    data object OnOKClicked : LoginEvent
}