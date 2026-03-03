package com.example.syncrun.ui.theme.screen.calendar

import androidx.lifecycle.ViewModel
import com.example.syncrun.ui.theme.component.NavMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// Data class untuk sesi latihan
data class WorkoutSession(
    val type: String,
    val title: String,
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

    // Dummy List Hari dalam 1 Bulan (Oktober: 31 Hari)
    // Angka 0 di awal array ini untuk memberikan 'padding' hari kosong jika awal bulan tidak mulai di hari Minggu.
    // Misalnya Oktober 2026 mulai di hari Kamis, kita kasih padding kosong.
    val calendarDays = (1..31).toList()

    // Dummy Jadwal Latihan (Key = Tanggal, Value = List Sesi)
    private val _schedule = MutableStateFlow(
        mapOf(
            12 to listOf(WorkoutSession("EASY RUN", "Recovery Run", "5.0 km", "30:00", "06:00/km", true)),
            13 to listOf(WorkoutSession("STRENGTH", "Lower Body & Core", duration = "45:00", isCompleted = true)),
            14 to listOf(WorkoutSession("REST", "Active Recovery & Stretching")),
            15 to listOf(WorkoutSession("INTERVAL", "Track Speed Work", "7.0 km", "50:00", "04:45/km", false)),
            16 to listOf(WorkoutSession("EASY RUN", "Base Building", "8.0 km", "50:00", "06:15/km")),
            17 to listOf(WorkoutSession("REST", "Rest Day")),
            18 to listOf(WorkoutSession("LONG RUN", "Sunday Long", "15.0 km", "1:30:00", "06:30/km"))
        )
    )
    val schedule = _schedule.asStateFlow()

    fun updateNavMenu(menu: NavMenu) {
        _currentNavMenu.value = menu
    }

    fun selectDate(date: Int) {
        _selectedDate.value = date
    }
}