package com.atnzvdev.thousand.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atnzvdev.thousand.presentation.bookmarks.BookmarkScreen
import com.atnzvdev.thousand.presentation.components.ButtonNavBar
import com.atnzvdev.thousand.presentation.main.MainScreen
import com.atnzvdev.thousand.presentation.navigation.BookmarksRoute
import com.atnzvdev.thousand.presentation.navigation.HomeRoute
import com.atnzvdev.thousand.presentation.profile.ProfileScreen

@Composable
fun MainFlowScreen() {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            ButtonNavBar(
                navController = bottomNavController
            )
        }) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> { MainScreen(onNavigationBack = { bottomNavController.popBackStack() }) }
            composable<BookmarksRoute> { BookmarkScreen(onNavigationBack = { bottomNavController.popBackStack() }) }
            composable<BookmarksRoute> { ProfileScreen(onNavigationBack = { bottomNavController.popBackStack() }) }
        }

    }
}