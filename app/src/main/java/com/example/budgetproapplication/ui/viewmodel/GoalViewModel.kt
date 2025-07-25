package com.example.budgetproapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetproapplication.data.model.SpendingGoal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class GoalViewModel : ViewModel() {
    private val _currentGoal = MutableStateFlow<SpendingGoal?>(null)
    val currentGoal: StateFlow<SpendingGoal?> = _currentGoal.asStateFlow()

    fun setGoal(minAmount: String, maxAmount: String) {
        viewModelScope.launch {
            val minAmountDouble = minAmount.toDoubleOrNull() ?: 0.0
            val maxAmountDouble = maxAmount.toDoubleOrNull() ?: 0.0
            
            if (minAmountDouble > 0 && maxAmountDouble > minAmountDouble) {
                val currentDate = java.time.LocalDate.now()
                val newGoal = SpendingGoal(
                    id = UUID.randomUUID().toString(),
                    minAmount = minAmountDouble,
                    maxAmount = maxAmountDouble,
                    month = currentDate.monthValue,
                    year = currentDate.year
                )
                _currentGoal.value = newGoal
            }
        }
    }
} 