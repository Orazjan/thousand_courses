package com.atnzvdev.thousand.presentation.main

sealed interface MainEvent {
    data class ShowSnackbar(val message: String) : MainEvent
}