package com.atnzvdev.thousand.presentation.main

sealed interface MainAction {
    data class OnCourseClicked(val courseId: Long) : MainAction
    data class ToggleBookmark(val courseId: Long) : MainAction
    data object SortByPublishDate : MainAction
    data object Retry : MainAction


}