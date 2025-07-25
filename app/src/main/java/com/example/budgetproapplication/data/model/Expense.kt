package com.example.budgetproapplication.data.model

import java.time.LocalDate

data class Expense(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val date: LocalDate = LocalDate.now(),
    val description: String = "",
    val category: String = ""
) 