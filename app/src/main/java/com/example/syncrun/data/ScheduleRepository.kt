package com.example.syncrun.data

import com.example.syncrun.ui.theme.screen.calendar.WorkoutSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

object ScheduleRepository {
    private val _schedule = MutableStateFlow<Map<Int, List<WorkoutSession>>>(
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

    /**
     * Menambahkan jadwal kuliah secara berulang untuk bulan tersebut.
     * Jika terdeteksi di hari Senin, maka semua hari Senin di bulan tersebut akan diisi.
     */
    fun updateSchedule(newSchedule: Map<Int, List<WorkoutSession>>) {
        val current = _schedule.value.toMutableMap()
        
        newSchedule.forEach { (date, sessions) ->
            val localDate = LocalDate.of(2026, 2, date)
            val dayOfWeek = localDate.dayOfWeek
            
            // Loop untuk semua hari di bulan Februari 2026 (28 hari)
            for (day in 1..28) {
                val currentLoopDate = LocalDate.of(2026, 2, day)
                if (currentLoopDate.dayOfWeek == dayOfWeek) {
                    val existingSessions = current[day] ?: emptyList()
                    
                    // Filter sesi baru agar tidak duplikat dengan yang sudah ada di tanggal tersebut
                    val filteredNewSessions = sessions.filter { newSession ->
                        existingSessions.none { it.title == newSession.title && it.duration == newSession.duration }
                    }
                    
                    current[day] = existingSessions + filteredNewSessions
                }
            }
        }
        _schedule.value = current
    }

    /**
     * Menambahkan agenda tunggal (misal untuk kelas pengganti atau jadwal manual)
     */
    fun addSingleEvent(date: Int, session: WorkoutSession) {
        val current = _schedule.value.toMutableMap()
        val existingSessions = current[date] ?: emptyList()
        current[date] = existingSessions + session
        _schedule.value = current
    }
}
