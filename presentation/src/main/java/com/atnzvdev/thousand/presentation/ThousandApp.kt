package com.atnzvdev.thousand.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atnzvdev.thousand.presentation.navigation.MainGraph
import com.atnzvdev.thousand.presentation.navigation.authGraph
import com.atnzvdev.thousand.presentation.theme.ThousandTheme

@Composable
fun ThousandApp() {
    ThousandTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = MainGraph) {
            authGraph(navController)

            composable<MainGraph> {
                MainFlowScreen()
            }
        }
    }
}