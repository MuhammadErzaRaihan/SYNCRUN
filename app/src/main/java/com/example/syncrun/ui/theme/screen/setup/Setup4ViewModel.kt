package com.example.syncrun.ui.theme.screen.setup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Setup4ViewModel : ViewModel() {
    private val _wakeUpTime = MutableStateFlow("")
    val wakeUpTime = _wakeUpTime.asStateFlow()

    private val _budget = MutableStateFlow("")
    val budget = _budget.asStateFlow()

    private val _otherConditions = MutableStateFlow("")
    val otherConditions = _otherConditions.asStateFlow()

    private val _selectedMedical = MutableStateFlow(setOf("NONE"))
    val selectedMedical = _selectedMedical.asStateFlow()

    fun updateWakeUpTime(time: String) { _wakeUpTime.value = time }
    fun updateBudget(amount: String) { _budget.value = amount }
    fun updateOtherConditions(text: String) { _otherConditions.value = text }

    fun toggleMedicalCondition(condition: String) {
        val current = _selectedMedical.value.toMutableSet()
        if (condition == "NONE") {
            // Jika pilih NONE, hapus pilihan lain
            _selectedMedical.value = setOf("NONE")
        } else {
            // Jika pilih penyakit tertentu, hapus NONE
            current.remove("NONE")
            if (current.contains(condition)) current.remove(condition) else current.add(condition)
            // Jika semua di-deselect, kembali ke NONE
            if (current.isEmpty()) current.add("NONE")
            _selectedMedical.value = current
        }
    }
}