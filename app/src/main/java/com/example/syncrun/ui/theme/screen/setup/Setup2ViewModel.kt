package com.example.syncrun.ui.theme.screen.setup


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Setup2ViewModel : ViewModel() {
    private val _easyPace = MutableStateFlow("00:00")
    val easyPace = _easyPace.asStateFlow()

    private val _isCalculated = MutableStateFlow(false)
    val isCalculated = _isCalculated.asStateFlow()

    fun updatePace(newValue: String) { _easyPace.value = newValue }
    fun calculateAI() { _isCalculated.value = true }
}