package com.example.syncrun.ui.theme.screen.calendar

import androidx.lifecycle.ViewModel
import com.example.syncrun.ui.theme.component.NavMenu
import com.example.syncrun.ui.theme.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// Data class untuk sesi latihan
// Di file CalendarViewModel.kt atau file model terpisah
data class WorkoutSession(
    val type: String, // Nanti diisi "CLASS" dari hasil OCR
    val title: String,
    val time: String? = null, // Tambahkan ini untuk jam kuliah
    val distance: String? = null,
    val duration: String? = null,
    val pace: String? = null,
    val isCompleted: Boolean = false
)

class CalendarViewModel : ViewModel() {
    private val _currentNavMenu = MutableStateFlow(NavMenu.CALENDAR)
    val currentNavMenu = _currentNavMenu.asStateFlow()

    // State Hari yang Dipilih (Default hari ini: 15)
    private val _selectedDate = MutableStateFlow(15)
    val selectedDate = _selectedDate.asStateFlow()

    val calendarDays = (1..31).toList()

    // BACA DARI REPOSITORY
    val schedule = ScheduleRepository.schedule



    fun updateNavMenu(menu: NavMenu) {
        _currentNavMenu.value = menu
    }

    fun selectDate(date: Int) {
        _selectedDate.value = date
    }
}