package com.example.budgetproapplication.data.model

data class SpendingGoal(
    val id: String = "",
    val minAmount: Double = 0.0,
    val maxAmount: Double = 0.0,
    val month: Int = 0,
    val year: Int = 0
) 