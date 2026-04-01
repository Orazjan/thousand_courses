package com.atnzvdev.thousand.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.atnzvdev.thousand.presentation.bookmarks.BookmarkScreen
import com.atnzvdev.thousand.presentation.main.MainScreen
import com.atnzvdev.thousand.presentation.profile.ProfileScreen

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation<MainGraph>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            MainScreen(
                onNavigationBack = { navController.popBackStack() }
            )
        }
        composable<BookmarksRoute> {
            BookmarkScreen(onNavigationBack = { navController.popBackStack() })
        }
        composable<ProfileRoute> {
            ProfileScreen(onNavigationBack = { navController.popBackStack() })
        }
    }
}