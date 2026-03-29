package com.example.syncrun.ui.theme.screen.setup

import com.example.syncrun.ui.theme.screen.calendar.WorkoutSession
import java.time.DayOfWeek
import java.time.LocalDate

object KrsParser {
    fun parseTextToSchedule(text: String): Map<Int, List<WorkoutSession>> {
        val lines = text.lines().map { it.trim() }.filter { it.isNotEmpty() }
        val detectedSchedule = mutableMapOf<Int, MutableList<WorkoutSession>>()
        
        // --- LOGIKA PENENTUAN TANGGAL BERDASARKAN HARI (Untuk Feb 2026) ---
        // Senin di Feb 2026 jatuh pada tanggal: 2, 9, 16, 23
        fun getDatesForDay(dayName: String): List<Int> {
            return when (dayName.lowercase()) {
                "senin", "monday" -> listOf(2, 9, 16, 23)
                "selasa", "tuesday" -> listOf(3, 10, 17, 24)
                "rabu", "wednesday" -> listOf(4, 11, 18, 25)
                "kamis", "thursday" -> listOf(5, 12, 19, 26)
                "jumat", "friday" -> listOf(6, 13, 20, 27)
                "sabtu", "saturday" -> listOf(7, 14, 21, 28)
                "minggu", "sunday" -> listOf(1, 8, 15, 22) // 1 Feb adalah Minggu
                else -> emptyList()
            }
        }

        // --- FORMAT 1 (Portal Akademik / Deteksi Berdasarkan Nama Hari "SELASA") ---
        var currentDetectedDayFromHeader: String? = null
        val allDays = listOf("senin", "selasa", "rabu", "kamis", "jumat", "sabtu", "minggu", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday")
        
        for (i in lines.indices) {
            val line = lines[i]
            val lowerLine = line.lowercase()

            // Cek jika baris adalah Nama Hari (Header seperti di Gambar 1)
            if (allDays.contains(lowerLine)) {
                currentDetectedDayFromHeader = lowerLine
                continue
            }

            // Deteksi Jam (07:20 - 09:00)
            val timeMatch = Regex("""(\d{1,2}[:.]\d{2})\s*-\s*(\d{1,2}[:.]\d{2})""").find(line)
            
            if (timeMatch != null && currentDetectedDayFromHeader != null) {
                val timeStr = timeMatch.value
                val targetDates = getDatesForDay(currentDetectedDayFromHeader)
                
                // Cari Judul (biasanya 2-3 baris di atas jam)
                var subject = ""
                for (j in i - 1 downTo maxOf(0, i - 4)) {
                    val l = lines[j]
                    if (l.length > 5 && !allDays.contains(l.lowercase()) && !l.contains(" - ")) {
                        subject = l
                        break
                    }
                }

                // Cari Detail lain (F2F, Session, Location)
                var classCode = ""; var deliveryMode = ""; var sessionLabel = ""; var location = ""
                for (k in maxOf(0, i - 5) until i + 3) {
                    if (k >= lines.size) break
                    val l = lines[k]
                    val lowL = l.lowercase()
                    when {
                        l.contains(" - ") && l.length < 15 -> classCode = l
                        lowL.contains("f2f") || lowL.contains("gslc") -> deliveryMode = l
                        lowL.contains("session") -> sessionLabel = l
                        lowL.contains("campus") || lowL.contains("main") -> location = l
                    }
                }

                if (subject.isNotEmpty()) {
                    targetDates.forEach { date ->
                        val session = WorkoutSession(
                            type = "CLASS", title = subject, duration = timeStr,
                            classCode = classCode, deliveryMode = deliveryMode,
                            session = sessionLabel, location = location, isCompleted = false
                        )
                        val list = detectedSchedule.getOrPut(date) { mutableListOf() }
                        if (list.none { it.title == subject && it.duration == timeStr }) list.add(session)
                    }
                }
            }
        }

        // --- FORMAT 2 (Binus Mobile - Deteksi Berdasarkan Tanggal Spesifik "02 February 2026") ---
        var lastDetectedDay: Int? = null
        val monthNames = listOf("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december")
        
        for (i in lines.indices) {
            val line = lines[i]
            val dateMatch = Regex("""(\d{1,2})\s+(${monthNames.joinToString("|")})\s+(\d{4})""", RegexOption.IGNORE_CASE).find(line)
            if (dateMatch != null) {
                lastDetectedDay = dateMatch.groupValues[1].toInt()
                continue
            }

            val timeMatch = Regex("""(\d{1,2}[:.]\d{2})\s*-\s*(\d{1,2}[:.]\d{2})(.*)""").find(line)
            if (timeMatch != null && lastDetectedDay != null) {
                val timeStr = timeMatch.value
                val currentDate = lastDetectedDay
                
                // Ambil Header (LE51 - LEC)
                var headerIndex = -1
                var classCode = ""
                for (j in i - 1 downTo maxOf(0, i - 10)) {
                    if (lines[j].contains(" - ") && lines[j].length < 15) {
                        headerIndex = j
                        classCode = lines[j]
                        break
                    }
                }

                if (headerIndex != -1) {
                    var subject = ""; var deliveryMode = ""; var sessionLabel = ""; var location = ""; var status = ""
                    for (k in headerIndex + 1 until i) {
                        val original = lines[k]
                        val l = original.lowercase()
                        when {
                            l.contains("f2f") || l.contains("gslc") -> deliveryMode = original
                            l.contains("session") -> sessionLabel = original
                            l.contains("campus") || l.contains("sutera") -> location = original
                            l.contains("class") -> status = original
                            original.length > 5 && subject.isEmpty() -> subject = original
                        }
                    }
                    
                    if (subject.isNotEmpty()) {
                        val session = WorkoutSession(
                            type = "CLASS", title = subject, duration = timeStr,
                            classCode = classCode, deliveryMode = deliveryMode,
                            session = sessionLabel, location = location, status = status, isCompleted = false
                        )
                        val list = detectedSchedule.getOrPut(currentDate) { mutableListOf() }
                        if (list.none { it.title == subject && it.duration == timeStr }) list.add(session)
                    }
                }
            }
        }
        return detectedSchedule
    }
}
