package com.atnzvdev.thousand.presentation.navigation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.atnzvdev.thousand.presentation.auth.login.LoginScreen
import com.atnzvdev.thousand.presentation.auth.login.SocialProvider
import com.atnzvdev.thousand.presentation.auth.registration.RegistrationScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<AuthGraph>(startDestination = LoginRoute) {
        composable<LoginRoute> {
            val context = LocalContext.current
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(MainGraph) {
                        popUpTo<AuthGraph> { inclusive = true }
                    }
                },
                onNavigateToRegistration = { navController.navigate(RegisterRoute) },
                onNavigateToResetPassword = { navController.navigate(ResetPasswordRoute) },
                onOpenExternalAuth = { provider ->
                    val url = when (provider) {
                        SocialProvider.VK -> "https://vk.com/"
                        SocialProvider.OK -> "https://ok.ru/"
                    }
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "Нет браузера", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            composable<RegisterRoute> {
                RegistrationScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}