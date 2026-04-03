package com.atnzvdev.thousand.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atnzvdev.thousand.presentation.components.CourseCard
import com.atnzvdev.thousand.presentation.components.SearchAndFilterRow
import com.atnzvdev.thousand.presentation.components.SortRow

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreenContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun MainScreenContent(
    state: MainUiState, onAction: (MainAction) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when (val screenState = state.screenState) {
            is ScreenState.Loading -> {
                MainLoadingContent(innerPadding = innerPadding)

            }

            is ScreenState.Content -> {
                MainCoursesContent(
                    innerPadding = innerPadding,
                    courses = screenState.courses,
                    onAction = onAction
                )
            }

            is ScreenState.Error -> {
                MainErrorContent(
                    innerPadding = innerPadding,
                    message = screenState.message
                )
            }
        }
    }
}

@Composable
private fun MainLoadingContent(
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun MainErrorContent(
    innerPadding: PaddingValues,
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun MainCoursesContent(
    innerPadding: PaddingValues,
    courses: List<CourseUiModel>,
    onAction: (MainAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        stickyHeader(key = "header_search") {
            MainHeader(
                onSortClick = {
                    onAction(MainAction.SortByPublishDate)
                }
            )
        }

        items(
            items = courses,
            key = { it.id }
        ) { course ->
            CourseCard(
                course = course,
                onClick = {
                    onAction(MainAction.OnCourseClicked(course.id))
                },
                onBookmarkClick = {
                    onAction(MainAction.ToggleBookmark(course.id))
                }
            )
        }
    }
}

@Composable
private fun MainHeader(
    onSortClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 8.dp)
    ) {
        SearchAndFilterRow(
            query = "",
            onQueryChange = {},
            onFilterClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        SortRow(
            onSortClick = onSortClick
        )
    }
}