package com.atnzvdev.thousand.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.atnzvdev.thousand.presentation.bookmarks.BookmarkScreen
import com.atnzvdev.thousand.presentation.components.MainBottomNavigation
import com.atnzvdev.thousand.presentation.main.MainScreen
import com.atnzvdev.thousand.presentation.navigation.AuthGraph
import com.atnzvdev.thousand.presentation.navigation.BookmarksRoute
import com.atnzvdev.thousand.presentation.navigation.HomeRoute
import com.atnzvdev.thousand.presentation.navigation.MainGraph
import com.atnzvdev.thousand.presentation.navigation.ProfileRoute
import com.atnzvdev.thousand.presentation.navigation.authGraph
import com.atnzvdev.thousand.presentation.profile.ProfileScreen
import com.atnzvdev.thousand.presentation.theme.ThousandTheme

@Composable
fun ThousandApp() {
    ThousandTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val showBottomBar = currentDestination?.let { dest ->
            dest.hasRoute<HomeRoute>() ||
                    dest.hasRoute<BookmarksRoute>() ||
                    dest.hasRoute<ProfileRoute>()
        } ?: false
        Scaffold(
            bottomBar = {
                AnimatedVisibility(showBottomBar) {
                    MainBottomNavigation(navController, currentDestination)
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AuthGraph,
                modifier = Modifier.padding(innerPadding)
            ) {
                authGraph(navController)

                navigation<MainGraph>(startDestination = HomeRoute) {
                    composable<HomeRoute> { MainScreen() }
                    composable<BookmarksRoute> { BookmarkScreen() }
                    composable<ProfileRoute> {
                        ProfileScreen(onNavigationBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}