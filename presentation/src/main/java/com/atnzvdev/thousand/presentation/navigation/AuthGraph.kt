package com.atnzvdev.thousand.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.atnzvdev.thousand.presentation.auth.login.LoginScreen
import com.atnzvdev.thousand.presentation.auth.registration.RegistrationScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<AuthGraph>(startDestination = LoginRoute) {
        composable<LoginRoute> {
            LoginScreen(
                onNavigateBack = { navController.popBackStack() },
                navigateToRegistration = { navController.navigate(RegisterRoute) },

                onLoginSuccess = {
                    navController.navigate(MainGraph) {
                        popUpTo(AuthGraph) {
                            inclusive = true
                        }
                    }
                })
            composable<RegisterRoute> {
                RegistrationScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}