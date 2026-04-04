package com.atnzvdev.thousand.presentation.main

data class MainUiState(
    val screenState: ScreenState = ScreenState.Loading
)

sealed interface ScreenState {
    data object Loading : ScreenState
    data object Empty : ScreenState
    data class Content(val courses: List<CourseUiModel>) : ScreenState
    data class Error(val message: String) : ScreenState
}