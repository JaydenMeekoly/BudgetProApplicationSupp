package com.example.budgetproapplication.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgetproapplication.ui.screens.DashboardScreen
import com.example.budgetproapplication.ui.screens.HomeScreen
import com.example.budgetproapplication.ui.screens.LoginScreen
import com.example.budgetproapplication.ui.screens.SignupScreen
import com.example.budgetproapplication.ui.screens.AddExpenseScreen
import com.example.budgetproapplication.ui.screens.ViewExpensesScreen
import com.example.budgetproapplication.ui.viewmodel.AuthState
import com.example.budgetproapplication.ui.viewmodel.AuthViewModel
import com.example.budgetproapplication.ui.viewmodel.ExpenseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object Signup : AuthScreen("signup")
    object Home : AuthScreen("home")
    object Dashboard : AuthScreen("dashboard")
    object AddExpense : AuthScreen("add_expense")
    object SetGoals : AuthScreen("set_goals")
    object ViewExpenses : AuthScreen("view_expenses")
    object ViewTotals : AuthScreen("view_totals")
}

@Composable
fun AuthNavigation(
    authViewModel: AuthViewModel,
    navController: NavHostController = rememberNavController()
) {
    val authState by authViewModel.authState.collectAsState()
    val expenseViewModel: ExpenseViewModel = viewModel()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate(AuthScreen.Dashboard.route) {
                    popUpTo(AuthScreen.Login.route) { inclusive = true }
                }
            }
            is AuthState.Initial -> {
                navController.navigate(AuthScreen.Login.route) {
                    popUpTo(AuthScreen.Dashboard.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (authState is AuthState.Authenticated) 
            AuthScreen.Dashboard.route else AuthScreen.Login.route
    ) {
        composable(AuthScreen.Login.route) {
            LoginScreen(
                onLoginClick = { email, password ->
                    authViewModel.signIn(email, password)
                },
                onSignupClick = {
                    navController.navigate(AuthScreen.Signup.route)
                },
                viewModel = authViewModel
            )
        }

        composable(AuthScreen.Signup.route) {
            SignupScreen(
                onSignupClick = { name, email, password ->
                    authViewModel.signUp(name, email, password)
                },
                onLoginClick = {
                    navController.popBackStack()
                },
                viewModel = authViewModel
            )
        }

        composable(AuthScreen.Home.route) {
            HomeScreen(viewModel = authViewModel)
        }

        composable(AuthScreen.Dashboard.route) {
            DashboardScreen(
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(AuthScreen.AddExpense.route) {
            AddExpenseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                expenseViewModel = expenseViewModel
            )
        }

        composable(AuthScreen.ViewExpenses.route) {
            ViewExpensesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                expenses = expenseViewModel.expenses.collectAsState().value
            )
        }

        composable(AuthScreen.SetGoals.route) {
            // TODO: Implement SetGoals screen
            Text("Set Goals Screen - Coming Soon")
        }

        composable(AuthScreen.ViewTotals.route) {
            // TODO: Implement ViewTotals screen
            Text("View Totals Screen - Coming Soon")
        }
    }
} 