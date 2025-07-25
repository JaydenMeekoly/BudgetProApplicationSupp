package com.example.budgetproapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetproapplication.ui.viewmodel.AuthState
import com.example.budgetproapplication.ui.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    viewModel: AuthViewModel
) {
    val authState by viewModel.authState.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (authState) {
            is AuthState.Authenticated -> {
                Text(
                    text = "Welcome to BudgetPro!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                currentUser?.let { user ->
                    Text(
                        text = "Name: ${user.name}",
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Email: ${user.email}",
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { viewModel.signOut() }
                ) {
                    Text("Sign Out")
                }
            }
            else -> {
                Text(
                    text = "Please sign in to continue",
                    fontSize = 18.sp
                )
            }
        }
    }
} 