package com.example.budgetproapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToAddExpense: () -> Unit,
    onNavigateToSetGoals: () -> Unit,
    onNavigateToViewExpenses: () -> Unit,
    onNavigateToViewTotals: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToBudgetProgress: () -> Unit,
    onNavigateToCategorySpendingGraph: () -> Unit
) {
    val options = listOf(
        DashboardOption(
            title = "Add Expense",
            icon = Icons.Default.Add,
            onClick = onNavigateToAddExpense
        ),
        DashboardOption(
            title = "View Expenses",
            icon = Icons.Default.List,
            onClick = onNavigateToViewExpenses
        ),
        DashboardOption(
            title = "Set Goals",
            icon = Icons.Default.Star,
            onClick = onNavigateToSetGoals
        ),
        DashboardOption(
            title = "View Totals",
            icon = Icons.Default.Star,
            onClick = onNavigateToViewTotals
        ),
        DashboardOption(
            title = "Budget Progress",
            icon = Icons.Default.List,
            onClick = onNavigateToBudgetProgress
        ),
        DashboardOption(
            title = "Category Spending Graph",
            icon = Icons.Default.Star,
            onClick = onNavigateToCategorySpendingGraph
        ),
        DashboardOption(
            title = "Logout",
            icon = Icons.Default.ExitToApp,
            onClick = onLogout
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(options) { option ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    onClick = option.onClick
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = option.icon,
                            contentDescription = option.title,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = option.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

data class DashboardOption(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
) 