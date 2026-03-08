package com.example.syncrun.ui.theme.repository

import com.example.syncrun.ui.theme.screen.calendar.WorkoutSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// Menggunakan object menjadikannya Singleton (hanya ada 1 instance di seluruh aplikasi)
object ScheduleRepository {

    // Pindahkan data dummy Anda ke sini sebagai data awal
    private val _schedule = MutableStateFlow<Map<Int, List<WorkoutSession>>>(
        mapOf(
            15 to listOf(WorkoutSession("INTERVAL", "Track Speed Work", duration = "50:00")),
            13 to listOf(WorkoutSession("STRENGTH", "Lower Body & Core", duration = "45:00", isCompleted = true)),
            14 to listOf(WorkoutSession("REST", "Active Recovery & Stretching")),
            16 to listOf(WorkoutSession("EASY RUN", "Base Building", "8.0 km", "50:00", "06:15/km")),
            17 to listOf(WorkoutSession("REST", "Rest Day")),
            18 to listOf(WorkoutSession("LONG RUN", "Sunday Long", "15.0 km", "1:30:00", "06:30/km"))
        )
    )
    val schedule = _schedule.asStateFlow()

    // Fungsi untuk menambah jadwal baru dari OCR
    fun addSchedule(date: Int, session: WorkoutSession) {
        val currentSchedule = _schedule.value.toMutableMap()
        val currentDaySessions = currentSchedule[date]?.toMutableList() ?: mutableListOf()

        currentDaySessions.add(session)
        currentSchedule[date] = currentDaySessions

        _schedule.value = currentSchedule // Update flow agar UI Calendar bereaksi
    }
}