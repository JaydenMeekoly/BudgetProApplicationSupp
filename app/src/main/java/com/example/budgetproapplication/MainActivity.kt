package com.example.budgetproapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.budgetproapplication.data.repository.AuthRepository
import com.example.budgetproapplication.ui.navigation.AuthNavigation
import com.example.budgetproapplication.ui.theme.BudgetProApplicationTheme
import com.example.budgetproapplication.ui.viewmodel.AuthViewModel
import com.example.budgetproapplication.ui.viewmodel.ExpenseViewModel
import com.example.budgetproapplication.ui.viewmodel.GoalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetProApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun MainScreen() {
    val navController = rememberNavController()
    val authRepository = AuthRepository()
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.provideFactory(authRepository)
    )
    val expenseViewModel: ExpenseViewModel = viewModel()
    val goalViewModel: GoalViewModel = viewModel()

    AuthNavigation(
        navController = navController,
        authViewModel = authViewModel,
        expenseViewModel = expenseViewModel,
        goalViewModel = goalViewModel
    )
}