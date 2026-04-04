package com.atnzvdev.thousand.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atnzvdev.domain.model.Course
import com.atnzvdev.domain.usecase.GetFavoriteCoursesUseCase
import com.atnzvdev.domain.usecase.ToggleFavoriteUseCase
import com.atnzvdev.thousand.presentation.main.CourseUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val courseUiMapper: CourseUiMapper
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookmarkUiState())
    val uiState = _uiState.asStateFlow()
    private var favoriteCourses: List<Course> = emptyList()
    private var observeCoursesJob: Job? = null

    init {
        observeFavoriteCourses()
    }

    fun onAction(action: BookmarkAction) {
        when (action) {
            is BookmarkAction.OnCourseClicked -> {}
            is BookmarkAction.Retry -> observeFavoriteCourses()
            is BookmarkAction.UnToggleBookmark -> unToggleBookmark(action.courseId)
        }
    }

    private fun observeFavoriteCourses() {
        observeCoursesJob?.cancel()
        observeCoursesJob = viewModelScope.launch {
            getFavoriteCoursesUseCase().collect { courses ->
                favoriteCourses = courses
                updateContentState(courses)
            }
        }
    }

    private fun updateContentState(courses: List<Course>) {
        val uiCourses = courseUiMapper.mapList(courses)

        val newState = if (uiCourses.isEmpty()) {
            BookmarkState.Empty
        } else {
            BookmarkState.Content(uiCourses)
        }

        _uiState.value = BookmarkUiState(bookmarkState = newState)
    }


    private fun unToggleBookmark(courseId: Int) {
        val course = favoriteCourses.find { it.id == courseId } ?: return

        viewModelScope.launch {
            toggleFavoriteUseCase(
                courseId = course.id, isFavorite = false
            )
        }
    }
}