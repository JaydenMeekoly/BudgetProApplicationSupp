package com.example.budgetproapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetproapplication.ui.viewmodel.ExpenseViewModel
import com.example.budgetproapplication.ui.viewmodel.GoalViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetProgressScreen(
    onNavigateBack: () -> Unit,
    expenseViewModel: ExpenseViewModel,
    goalViewModel: GoalViewModel
) {
    val expenses by expenseViewModel.expenses.collectAsState()
    val goalState by goalViewModel.currentGoal.collectAsState()

    val totalSpent = expenses.sumOf { it.price }
    val minGoal = goalState?.minAmount ?: 0.0
    val maxGoal = goalState?.maxAmount ?: 0.0
    val progress = if (maxGoal > 0) (totalSpent / maxGoal).coerceIn(0.0, 1.0) else 0.0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget Progress") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Total Spent: $${String.format("%.2f", totalSpent)}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Budget Limit: $${String.format("%.2f", maxGoal)}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(24.dp))
            LinearProgressIndicator(
                progress = progress.toFloat(),
                modifier = Modifier.fillMaxWidth().height(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (maxGoal > 0) {
                    "${(progress * 100).toInt()}% of your budget used"
                } else {
                    "No budget goal set."
                },
                style = MaterialTheme.typography.bodyLarge
            )
            if (maxGoal > 0 && totalSpent > maxGoal) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "You are over budget!",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
} 