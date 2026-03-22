package com.example.syncrun.ui.theme.screen.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncrun.data.ScheduleRepository
import com.example.syncrun.ui.theme.component.NavMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

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

    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth = _currentMonth.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    // Generate list of days for the current month, including leading empty days
    val calendarDays: StateFlow<List<Int?>> = _currentMonth.combine(_selectedDate) { month, _ ->
        val firstDayOfMonth = month.atDay(1)
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // 0 = Sunday, 1 = Monday, ...
        
        val daysInMonth = month.lengthOfMonth()
        val days = mutableListOf<Int?>()
        
        // Add leading empty days
        for (i in 0 until firstDayOfWeek) {
            days.add(null)
        }
        
        // Add actual days
        for (i in 1..daysInMonth) {
            days.add(i)
        }
        
        days
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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

    fun selectDate(day: Int) {
        _selectedDate.value = _currentMonth.value.atDay(day)
    }

    fun nextMonth() {
        _currentMonth.value = _currentMonth.value.plusMonths(1)
    }

    fun previousMonth() {
        _currentMonth.value = _currentMonth.value.minusMonths(1)
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
        // Note: ScheduleRepository currently uses day Int as key. 
        // We should ideally use full date, but for now we use the selected date's day.
        ScheduleRepository.addSingleEvent(_selectedDate.value.dayOfMonth, newSession)
        _showAddEventDialog.value = false
    }
}
