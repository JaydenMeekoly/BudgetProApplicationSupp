package com.example.budgetproapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetproapplication.data.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ExpenseViewModel : ViewModel() {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    fun addExpense(
        name: String,
        price: String,
        date: java.time.LocalDate,
        description: String,
        category: String
    ) {
        viewModelScope.launch {
            val priceDouble = price.toDoubleOrNull() ?: 0.0
            val newExpense = Expense(
                id = UUID.randomUUID().toString(),
                name = name,
                price = priceDouble,
                date = date,
                description = description,
                category = category
            )
            _expenses.value = _expenses.value + newExpense
        }
    }

    fun deleteExpense(expenseId: String) {
        viewModelScope.launch {
            _expenses.value = _expenses.value.filter { it.id != expenseId }
        }
    }
} 