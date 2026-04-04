package com.atnzvdev.thousand.presentation.bookmarks

sealed interface BookmarkAction {
    data class OnCourseClicked(val courseId: Int) : BookmarkAction
    data class UnToggleBookmark(val courseId: Int) : BookmarkAction
    data object Retry : BookmarkAction

}