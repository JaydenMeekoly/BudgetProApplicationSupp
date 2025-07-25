package com.example.budgetproapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.example.budgetproapplication.ui.viewmodel.GoalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetGoalsScreen(
    onNavigateBack: () -> Unit,
    goalViewModel: GoalViewModel
) {
    var minAmount by remember { mutableStateOf("") }
    var maxAmount by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { 
                showSuccessDialog = false
                onNavigateBack()
            },
            title = { Text("Success") },
            text = { Text("Goal created successfully") },
            confirmButton = {
                TextButton(
                    onClick = { 
                        showSuccessDialog = false
                        onNavigateBack()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Set Monthly Goals") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            Text(
                text = "Set your monthly spending goals",
                style = MaterialTheme.typography.titleMedium
            )

            // Minimum Amount
            OutlinedTextField(
                value = minAmount,
                onValueChange = { 
                    if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                        minAmount = it
                        errorMessage = null
                    }
                },
                label = { Text("Minimum Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                prefix = { Text("$") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage != null
            )

            // Maximum Amount
            OutlinedTextField(
                value = maxAmount,
                onValueChange = { 
                    if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                        maxAmount = it
                        errorMessage = null
                    }
                },
                label = { Text("Maximum Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                prefix = { Text("$") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage != null
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Create Goal Button
            Button(
                onClick = { 
                    val minAmountDouble = minAmount.toDoubleOrNull() ?: 0.0
                    val maxAmountDouble = maxAmount.toDoubleOrNull() ?: 0.0

                    when {
                        minAmount.isBlank() || maxAmount.isBlank() -> {
                            errorMessage = "Please fill in both amounts"
                        }
                        minAmountDouble <= 0 || maxAmountDouble <= 0 -> {
                            errorMessage = "Amounts must be greater than 0"
                        }
                        maxAmountDouble <= minAmountDouble -> {
                            errorMessage = "Maximum amount must be greater than minimum amount"
                        }
                        else -> {
                            goalViewModel.setGoal(minAmount, maxAmount)
                            showSuccessDialog = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Create Goal")
            }
        }
    }
} 