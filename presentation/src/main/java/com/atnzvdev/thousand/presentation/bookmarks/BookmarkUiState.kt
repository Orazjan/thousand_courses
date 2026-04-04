package com.atnzvdev.thousand.presentation.bookmarks

import com.atnzvdev.thousand.presentation.main.CourseUiModel

data class BookmarkUiState(
    val bookmarkState: BookmarkState = BookmarkState.Loading
)

sealed interface BookmarkState {
    data object Loading : BookmarkState
    data object Empty : BookmarkState
    data class Content(val courses: List<CourseUiModel>) : BookmarkState
    data class Error(val message: String) : BookmarkState
}