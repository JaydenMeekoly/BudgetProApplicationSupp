package com.example.budgetproapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.budgetproapplication.data.repository.AuthRepository
import com.example.budgetproapplication.ui.navigation.AuthNavigation
import com.example.budgetproapplication.ui.theme.BudgetProApplicationTheme
import com.example.budgetproapplication.ui.theme.rememberThemeState
import com.example.budgetproapplication.ui.viewmodel.AuthViewModel
import com.example.budgetproapplication.ui.viewmodel.ExpenseViewModel
import com.example.budgetproapplication.ui.viewmodel.GoalViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val authRepository = AuthRepository()
            val authViewModel: AuthViewModel = viewModel(
                factory = AuthViewModel.provideFactory(authRepository)
            )
            val expenseViewModel: ExpenseViewModel = viewModel()
            val goalViewModel: GoalViewModel = viewModel()
            
            val themeState = rememberThemeState()
            val coroutineScope = rememberCoroutineScope()
            
            BudgetProApplicationTheme(
                darkTheme = themeState.isDark,
                dynamicColor = true
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    Box(modifier = Modifier.fillMaxSize()) {
                        AuthNavigation(
                            navController = navController,
                            authViewModel = authViewModel,
                            expenseViewModel = expenseViewModel,
                            goalViewModel = goalViewModel
                        )
                        
                        // Theme toggle button
                        IconButton(
                            onClick = { 
                                coroutineScope.launch {
                                    themeState.toggleTheme()
                                }
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = if (themeState.isDark) Icons.Default.Brightness7 
                                          else Icons.Default.Brightness4,
                                contentDescription = "Toggle Theme",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}