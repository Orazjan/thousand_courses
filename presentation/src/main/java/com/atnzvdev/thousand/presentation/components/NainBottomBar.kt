package com.atnzvdev.thousand.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Bookmark
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.atnzvdev.thousand.presentation.navigation.BookmarksRoute
import com.atnzvdev.thousand.presentation.navigation.HomeRoute
import com.atnzvdev.thousand.presentation.navigation.ProfileRoute

@Composable
fun MainBottomNavigation(
    navController: NavController, currentDestination: NavDestination?
) {
    val navItemColors = NavigationBarItemDefaults.colors(
        selectedTextColor = MaterialTheme.colorScheme.onSurface,
        indicatorColor = MaterialTheme.colorScheme.surface,
        selectedIconColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant, tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple(HomeRoute, Icons.TwoTone.Home, "Главная"),
            Triple(BookmarksRoute, Icons.TwoTone.Bookmark, "Избранное"),
            Triple(ProfileRoute, Icons.TwoTone.Person, "Профиль")
        )

        items.forEach { (route, icon, label) ->
            NavigationBarItem(
                selected = currentDestination?.hasRoute(route::class) == true,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                colors = navItemColors
            )
        }
    }
}