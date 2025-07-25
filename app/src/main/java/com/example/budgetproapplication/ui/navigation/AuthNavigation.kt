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
import com.example.budgetproapplication.ui.screens.SetGoalsScreen
import com.example.budgetproapplication.ui.screens.ViewTotalsScreen
import com.example.budgetproapplication.ui.screens.BudgetProgressScreen
import com.example.budgetproapplication.ui.screens.CategorySpendingGraphScreen
import com.example.budgetproapplication.ui.viewmodel.AuthState
import com.example.budgetproapplication.ui.viewmodel.AuthViewModel
import com.example.budgetproapplication.ui.viewmodel.ExpenseViewModel
import com.example.budgetproapplication.ui.viewmodel.GoalViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("Login")
    object Signup : AuthScreen("Signup")
    object Dashboard : AuthScreen("Dashboard")
    object AddExpense : AuthScreen("AddExpense")
    object ViewExpenses : AuthScreen("ViewExpenses")
    object SetGoals : AuthScreen("SetGoals")
    object ViewTotals : AuthScreen("ViewTotals")
    object BudgetProgress : AuthScreen("BudgetProgress")
    object CategorySpendingGraph : AuthScreen("CategorySpendingGraph")
}

@Composable
fun AuthNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    expenseViewModel: ExpenseViewModel,
    goalViewModel: GoalViewModel
) {
    val authState by authViewModel.authState.collectAsState()

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
        startDestination = AuthScreen.Login.route
    ) {
        composable(AuthScreen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AuthScreen.Dashboard.route) {
                        popUpTo(AuthScreen.Login.route) { inclusive = true }
                    }
                },
                onSignupClick = {
                    navController.navigate(AuthScreen.Signup.route)
                },
                authViewModel = authViewModel
            )
        }
        composable(AuthScreen.Signup.route) {
            SignupScreen(
                onSignupClick = { name, email, password ->
                    authViewModel.signUp(name, email, password)
                    navController.navigate(AuthScreen.Dashboard.route) {
                        popUpTo(AuthScreen.Signup.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(AuthScreen.Signup.route) { inclusive = true }
                    }
                },
                viewModel = authViewModel
            )
        }

        composable(AuthScreen.Dashboard.route) {
            DashboardScreen(
                onNavigateToAddExpense = {
                    navController.navigate(AuthScreen.AddExpense.route)
                },
                onNavigateToSetGoals = {
                    navController.navigate(AuthScreen.SetGoals.route)
                },
                onNavigateToViewExpenses = {
                    navController.navigate(AuthScreen.ViewExpenses.route)
                },
                onNavigateToViewTotals = {
                    navController.navigate(AuthScreen.ViewTotals.route)
                },
                onLogout = {
                    authViewModel.signOut()
                },
                onNavigateToBudgetProgress = {
                    navController.navigate(AuthScreen.BudgetProgress.route)
                },
                onNavigateToCategorySpendingGraph = {
                    navController.navigate(AuthScreen.CategorySpendingGraph.route)
                }
            )
        }

        composable(AuthScreen.AddExpense.route) {
            AddExpenseScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                expenseViewModel = expenseViewModel
            )
        }

        composable(AuthScreen.ViewExpenses.route) {
            ViewExpensesScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                expenseViewModel = expenseViewModel
            )
        }

        composable(AuthScreen.SetGoals.route) {
            SetGoalsScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                goalViewModel = goalViewModel
            )
        }

        composable(AuthScreen.ViewTotals.route) {
            ViewTotalsScreen(
                navController = navController,
                expenseViewModel = expenseViewModel
            )
        }

        composable(AuthScreen.BudgetProgress.route) {
            BudgetProgressScreen(
                onNavigateBack = { navController.navigateUp() },
                expenseViewModel = expenseViewModel,
                goalViewModel = goalViewModel
            )
        }

        composable(AuthScreen.CategorySpendingGraph.route) {
            CategorySpendingGraphScreen(
                navController = navController
            )
        }
    }
} 