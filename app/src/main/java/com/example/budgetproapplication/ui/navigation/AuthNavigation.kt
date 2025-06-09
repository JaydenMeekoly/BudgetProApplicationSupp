package com.example.budgetproapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgetproapplication.ui.screens.LoginScreen
import com.example.budgetproapplication.ui.screens.SignupScreen

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object Signup : AuthScreen("signup")
}

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AuthScreen.Login.route
    ) {
        composable(AuthScreen.Login.route) {
            LoginScreen(
                onLoginClick = { email, password ->
                    // TODO: Implement login logic
                },
                onSignupClick = {
                    navController.navigate(AuthScreen.Signup.route)
                }
            )
        }

        composable(AuthScreen.Signup.route) {
            SignupScreen(
                onSignupClick = { name, email, password ->
                    // TODO: Implement signup logic
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
    }
} 