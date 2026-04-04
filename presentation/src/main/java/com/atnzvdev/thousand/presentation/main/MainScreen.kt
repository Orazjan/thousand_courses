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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.atnzvdev.thousand.presentation.components.CourseCard
import com.atnzvdev.thousand.presentation.components.PrimaryButton
import com.atnzvdev.thousand.presentation.components.SearchAndFilterRow
import com.atnzvdev.thousand.presentation.components.SortRow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collectLatest { event ->
                when (event) {
                    is MainEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
    MainScreenContent(
        paddingValues = innerPadding,
        state = state,
        onAction = viewModel::onAction
    )
}
}

@Composable
fun MainScreenContent(
    state: MainUiState, onAction: (MainAction) -> Unit, paddingValues: PaddingValues
) {
    when (val screenState = state.screenState) {
            is ScreenState.Loading -> {
                MainLoadingContent(innerPadding = paddingValues)
            }

            is ScreenState.Content -> {
                MainCoursesContent(
                    innerPadding = paddingValues,
                    courses = screenState.courses,
                    onAction = onAction
                )
            }

            is ScreenState.Error -> {
                MainErrorContent(
                    innerPadding = paddingValues,
                    message = screenState.message,
                    onAction = onAction
                )
            }

        is ScreenState.Empty -> {
            MainErrorContent(
                innerPadding = paddingValues, message = "Вознкла ошибка", onAction = onAction
            )
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
    message: String,
    onAction: (MainAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        MainHeader(onSortClick = { onAction(MainAction.SortByPublishDate) })

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                onClick = { onAction(MainAction.Retry) },
                modifier = Modifier.padding(horizontal = 24.dp),
                text = "Повторить попытку",
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}
@Composable
private fun MainCoursesContent(
    innerPadding: PaddingValues,
    courses: List<CourseUiModel>,
    onAction: (MainAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        MainHeader(
            onSortClick = {
                onAction(MainAction.SortByPublishDate)
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
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
}

@Composable
private fun MainHeader(
    onSortClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 16.dp)
            .padding(horizontal = 16.dp)
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