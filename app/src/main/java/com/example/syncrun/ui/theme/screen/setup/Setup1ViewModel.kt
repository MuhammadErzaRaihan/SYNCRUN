package com.example.syncrun.ui.theme.screen.setup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Setup1ViewModel : ViewModel() {
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _currentWeight = MutableStateFlow("")
    val currentWeight = _currentWeight.asStateFlow()

    private val _targetWeight = MutableStateFlow("")
    val targetWeight = _targetWeight.asStateFlow()

    private val _selectedGoal = MutableStateFlow("")
    val selectedGoal = _selectedGoal.asStateFlow()

    fun updateName(newValue: String) { _name.value = newValue }
    fun updateCurrentWeight(newValue: String) { _currentWeight.value = newValue }
    fun updateTargetWeight(newValue: String) { _targetWeight.value = newValue }
    fun updateGoal(newValue: String) { _selectedGoal.value = newValue }
}