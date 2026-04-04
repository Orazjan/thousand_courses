package com.atnzvdev.thousand.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atnzvdev.domain.model.Course
import com.atnzvdev.domain.usecase.FetchCoursesUseCase
import com.atnzvdev.domain.usecase.GetCoursesUseCase
import com.atnzvdev.domain.usecase.SortCoursesByPublishDateUseCase
import com.atnzvdev.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val fetchCoursesUseCase: FetchCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val sortCoursesByPublishDateUseCase: SortCoursesByPublishDateUseCase,
    private val courseUiMapper: CourseUiMapper

) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<MainEvent>()
    val event = _event.receiveAsFlow()

    private var isSortApplied = false
    private var latestCourses: List<Course> = emptyList()

    private var observeCoursesJob: Job? = null

    init {
        observeCourses()
        fetchCourses()
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.Retry -> {
                fetchCourses()
            }

            is MainAction.SortByPublishDate -> {
                sortCourses()
            }

            is MainAction.ToggleBookmark -> toggleFavorite(action.courseId)
            is MainAction.OnCourseClicked -> {
                _event.trySend(MainEvent.ShowSnackbar("Переход к деталям"))

            }
        }
    }

    private fun toggleFavorite(courseId: Long) {
        val currentCourses = latestCourses
        val course = currentCourses.find { it.id.toLong() == courseId } ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(
                courseId = course.id, isFavorite = !course.isFavorite
            )

        }
    }

    private fun observeCourses() {
        observeCoursesJob?.cancel()
        observeCoursesJob = viewModelScope.launch {
            getCoursesUseCase().collect { courses ->
                latestCourses = courses
                updateContentState(courses)
            }
        }
    }


    private fun fetchCourses() {
        viewModelScope.launch {
            val currentState = _uiState.value.screenState

            if (currentState !is ScreenState.Content)
                _uiState.value = MainUiState(ScreenState.Loading)

            val result = fetchCoursesUseCase()

            result.onFailure { throwable ->
                if (latestCourses.isEmpty()) {
                    _uiState.value = MainUiState(
                        ScreenState.Error(
                            "Не удалось загрузить курсы"
                        )
                    )
                } else {
                    _event.trySend(
                        MainEvent.ShowSnackbar(
                            "Не удалось обновить данные"
                        )
                    )
                }
            }
        }

    }

    private fun sortCourses() {
        isSortApplied = !isSortApplied
        updateContentState(latestCourses)
    }

    private fun updateContentState(courses: List<Course>) {
        val finalCourses = if (isSortApplied) {
            sortCoursesByPublishDateUseCase(courses)
        } else {
            courses
        }

        val uiCourses = courseUiMapper.mapList(finalCourses)

        _uiState.value = when {
            uiCourses.isNotEmpty() -> {
                MainUiState(ScreenState.Content(uiCourses))
            }

            _uiState.value.screenState is ScreenState.Error -> {
                _uiState.value
            }

            else -> {
                MainUiState(ScreenState.Empty)
            }
        }
    }

}