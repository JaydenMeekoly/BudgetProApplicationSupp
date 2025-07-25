package com.example.budgetproapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetproapplication.ui.viewmodel.ExpenseViewModel
import com.example.budgetproapplication.ui.viewmodel.GoalViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.entry.entryModelOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySpendingGraphScreen(
    onNavigateBack: () -> Unit,
    expenseViewModel: ExpenseViewModel,
    goalViewModel: GoalViewModel
) {
    val expenses by expenseViewModel.expenses.collectAsState()
    val goalState by goalViewModel.currentGoal.collectAsState()

    var startDate by remember { mutableStateOf(LocalDate.now().minusMonths(1)) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val filteredExpenses = remember(expenses, startDate, endDate) {
        expenses.filter { it.date in startDate..endDate }
    }
    // Group expenses by category and calculate total amount for each category
    val (categoryList, values) = remember(expenses) {
        val totals = expenses.groupBy { it.category }
            .mapValues { (_, expenses) -> expenses.sumOf { it.price } }
            .toList()
            .sortedByDescending { it.second }
            .take(10) // Limit to top 10 categories
        
        val categories = totals.map { it.first }
        val amounts = totals.map { it.second.toFloat() }
        
        Pair(categories, amounts)
    }
    
    val chartEntryModel = remember(categoryList, values) {
        entryModelOf(*values.toTypedArray())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Spending by Category") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = { showStartDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("From: ${startDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}")
                }
                OutlinedButton(onClick = { showEndDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("To: ${endDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}")
                }
            }
            if (categoryList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No expenses in selected period.")
                }
            } else {
                // Chart
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp)
                ) {
                    // Simple chart implementation with minimal configuration
                    Chart(
                        chart = columnChart(),
                        model = chartEntryModel,
                        startAxis = startAxis(
                            valueFormatter = { value, _ -> "$${value.toInt()}" }
                        ),
                        bottomAxis = bottomAxis(
                            valueFormatter = { value, _ ->
                                val index = value.toInt()
                                if (index in categoryList.indices) {
                                    categoryList[index].take(10) + if (categoryList[index].length > 10) "..." else ""
                                } else ""
                            },
                            labelRotationDegrees = 45f
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        if (showStartDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showStartDatePicker = false },
                confirmButton = {
                    TextButton(onClick = { showStartDatePicker = false }) { Text("OK") }
                }
            ) {
                // Placeholder: Use your preferred date picker implementation
                // Set startDate here
            }
        }
        if (showEndDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showEndDatePicker = false },
                confirmButton = {
                    TextButton(onClick = { showEndDatePicker = false }) { Text("OK") }
                }
            ) {
                // Placeholder: Use your preferred date picker implementation
                // Set endDate here
            }
        }
    }
} 