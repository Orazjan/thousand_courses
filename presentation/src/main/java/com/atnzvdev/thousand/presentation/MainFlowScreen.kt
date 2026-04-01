package com.atnzvdev.thousand.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.atnzvdev.thousand.presentation.bookmarks.BookmarkScreen
import com.atnzvdev.thousand.presentation.main.MainScreen
import com.atnzvdev.thousand.presentation.navigation.BookmarksRoute
import com.atnzvdev.thousand.presentation.navigation.HomeRoute
import com.atnzvdev.thousand.presentation.navigation.ProfileRoute
import com.atnzvdev.thousand.presentation.profile.ProfileScreen

@Composable
fun MainFlowScreen() {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar =
        currentDestination?.hasRoute(HomeRoute::class) == true || currentDestination?.hasRoute(
            BookmarksRoute::class
        ) == true || currentDestination?.hasRoute(ProfileRoute::class) == true
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                showBottomBar, enter = slideInVertically(
                    initialOffsetY = { it }, animationSpec = spring(stiffness = Spring.StiffnessLow)
                ), exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                    tonalElevation = 8.dp,
                ) {
                    val navItemColors = NavigationBarItemDefaults.colors(
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )

                    NavigationBarItem(
                        selected = currentDestination?.hasRoute(HomeRoute::class) == true,
                        onClick = {
                            bottomNavController.navigate(HomeRoute) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = "Home Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                modifier = Modifier.height(15.dp),
                                text = "Главная", style = MaterialTheme.typography.labelSmall
                            )
                        },
                        colors = navItemColors
                    )

                    NavigationBarItem(

                        selected = currentDestination?.hasRoute(BookmarksRoute::class) == true,
                        onClick = {
                            bottomNavController.navigate(BookmarksRoute) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Favorite Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                modifier = Modifier.height(15.dp),
                                text = "Избранное", style = MaterialTheme.typography.labelSmall
                            )
                        },
                        colors = navItemColors
                    )

                    NavigationBarItem(
                        selected = currentDestination?.hasRoute(ProfileRoute::class) == true,
                        onClick = {
                            bottomNavController.navigate(ProfileRoute) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Profile Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                modifier = Modifier.height(15.dp),
                                text = "Профиль", style = MaterialTheme.typography.labelSmall
                            )
                        },
                        colors = navItemColors
                    )
                }
            }
        }) { innerPadding ->

        NavHost(
            navController = bottomNavController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> { MainScreen(onNavigationBack = { bottomNavController.popBackStack() }) }
            composable<BookmarksRoute> { BookmarkScreen(onNavigationBack = { bottomNavController.popBackStack() }) }
            composable<ProfileRoute> { ProfileScreen(onNavigationBack = { bottomNavController.popBackStack() }) }
        }
    }
}