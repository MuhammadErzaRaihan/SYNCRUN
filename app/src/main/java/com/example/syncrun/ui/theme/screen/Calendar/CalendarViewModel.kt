package com.example.syncrun.ui.theme.screen.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncrun.data.ScheduleRepository
import com.example.syncrun.ui.theme.component.NavMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Data class untuk sesi latihan dan jadwal kelas
data class WorkoutSession(
    val type: String,
    val title: String,
    val distance: String? = null,
    val duration: String? = null,
    val pace: String? = null,
    val isCompleted: Boolean = false,
    
    // Tambahan detail untuk jadwal kelas (Binus Style)
    val classCode: String? = null,
    val deliveryMode: String? = null,
    val session: String? = null,
    val location: String? = null,
    val status: String? = null
)

class CalendarViewModel : ViewModel() {
    private val _currentNavMenu = MutableStateFlow(NavMenu.CALENDAR)
    val currentNavMenu = _currentNavMenu.asStateFlow()

    private val _selectedDate = MutableStateFlow(15)
    val selectedDate = _selectedDate.asStateFlow()

    val calendarDays = (1..31).toList()

    private val _schedule = MutableStateFlow<Map<Int, List<WorkoutSession>>>(emptyMap())
    val schedule = _schedule.asStateFlow()

    // State untuk Dialog Add Event
    private val _showAddEventDialog = MutableStateFlow(false)
    val showAddEventDialog = _showAddEventDialog.asStateFlow()

    init {
        viewModelScope.launch {
            ScheduleRepository.schedule.collect {
                _schedule.value = it
            }
        }
    }

    fun updateNavMenu(menu: NavMenu) {
        _currentNavMenu.value = menu
    }

    fun selectDate(date: Int) {
        _selectedDate.value = date
    }

    fun toggleAddEventDialog(show: Boolean) {
        _showAddEventDialog.value = show
    }

    fun addNewClass(
        title: String,
        time: String,
        classCode: String = "",
        deliveryMode: String = "",
        session: String = "",
        location: String = "",
        status: String = ""
    ) {
        val newSession = WorkoutSession(
            type = "CLASS",
            title = title,
            duration = time,
            classCode = if (classCode.isNotEmpty()) classCode else null,
            deliveryMode = if (deliveryMode.isNotEmpty()) deliveryMode else null,
            session = if (session.isNotEmpty()) session else null,
            location = if (location.isNotEmpty()) location else null,
            status = if (status.isNotEmpty()) status else null,
            isCompleted = false
        )
        ScheduleRepository.addSingleEvent(_selectedDate.value, newSession)
        _showAddEventDialog.value = false
    }
}
