package com.example.syncrun.ui.theme.screen.setup

import com.example.syncrun.ui.theme.screen.calendar.WorkoutSession

object KrsParser {
    fun parseTextToSchedule(text: String): Map<Int, List<WorkoutSession>> {
        val lines = text.lines().map { it.trim() }.filter { it.isNotEmpty() }
        val detectedSchedule = mutableMapOf<Int, MutableList<WorkoutSession>>()
        
        // --- FORMAT 1 (Portal Akademik - Fallback) ---
        val daysMap = mapOf(
            "senin" to listOf(5, 12, 19, 26),
            "selasa" to listOf(6, 13, 20, 27),
            "rabu" to listOf(7, 14, 21, 28),
            "kamis" to listOf(1, 8, 15, 22, 29),
            "jumat" to listOf(2, 9, 16, 23, 30),
            "sabtu" to listOf(3, 10, 17, 24, 31),
            "minggu" to listOf(4, 11, 18, 25)
        )

        var potentialSubject = ""
        for (i in lines.indices) {
            val line = lines[i]
            val lowerLine = line.lowercase()
            if (lowerLine == "dosen ampu" && i > 0) potentialSubject = lines[i - 1]
            if (lowerLine == "jadwal" && i + 2 < lines.size) {
                val dayStr = lines[i + 1].lowercase()
                val timeStr = lines[i + 2]
                val dayEntry = daysMap.entries.find { dayStr.contains(it.key) }
                if (dayEntry != null) {
                    val displaySubject = potentialSubject.ifEmpty { "Mata Kuliah" }
                    dayEntry.value.forEach { date ->
                        val session = WorkoutSession(type = "CLASS", title = displaySubject, duration = timeStr, isCompleted = false)
                        val list = detectedSchedule.getOrPut(date) { mutableListOf() }
                        if (list.none { it.title == displaySubject && it.duration == timeStr }) list.add(session)
                    }
                }
            }
        }

        // --- FORMAT 2 (Binus Mobile / Modern) ---
        var lastDetectedDay: Int? = null
        val monthNames = listOf("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december")
        
        for (i in lines.indices) {
            val line = lines[i]
            
            // 1. Deteksi Tanggal (02 February 2026)
            val dateMatch = Regex("""(\d{1,2})\s+(${monthNames.joinToString("|")})\s+(\d{4})""", RegexOption.IGNORE_CASE).find(line)
            if (dateMatch != null) {
                lastDetectedDay = dateMatch.groupValues[1].toInt()
                continue
            }

            // 2. Deteksi Jam (11:20 - 13:00 GMT+7)
            val timeMatch = Regex("""(\d{1,2}[:.]\d{2})\s*-\s*(\d{1,2}[:.]\d{2})(.*)""").find(line)
            val currentDate = lastDetectedDay
            
            if (timeMatch != null && currentDate != null) {
                val timeStr = timeMatch.value
                
                // Cari Header Kelas ke atas (misal "LE51 - LEC")
                var headerIndex = -1
                var classCode = ""
                for (j in i - 1 downTo maxOf(0, i - 15)) {
                    val l = lines[j]
                    if (l.contains(" - ") && l.any { it.isUpperCase() } && l.length < 30) {
                        headerIndex = j
                        classCode = l
                        break
                    }
                }

                if (headerIndex != -1) {
                    var subject = ""
                    var deliveryMode = ""
                    var sessionLabel = ""
                    var location = ""
                    var status = ""

                    // Scan baris di antara Header dan Time
                    for (k in headerIndex + 1 until i) {
                        val original = lines[k]
                        val l = original.lowercase()
                        
                        when {
                            l.contains("f2f") || l.contains("gslc") || l.contains("online") -> deliveryMode = original
                            l.contains("session") -> sessionLabel = original
                            l.contains("campus") || l.contains("alam sutera") || l.contains("main") -> location = original
                            l.contains("onsite class") || l.contains("online class") -> status = original
                            original.length > 5 && subject.isEmpty() -> subject = original
                        }
                    }
                    
                    // Kadang location ada di bawah time
                    if (location.isEmpty() && i + 1 < lines.size) {
                        val nextLine = lines[i+1]
                        if (nextLine.lowercase().contains("campus")) location = nextLine
                    }

                    if (subject.isNotEmpty()) {
                        val session = WorkoutSession(
                            type = "CLASS", 
                            title = subject, 
                            duration = timeStr,
                            classCode = classCode,
                            deliveryMode = deliveryMode,
                            session = sessionLabel,
                            location = location,
                            status = status,
                            isCompleted = false
                        )
                        val list = detectedSchedule.getOrPut(currentDate) { mutableListOf() }
                        if (list.none { it.title == subject && it.duration == timeStr }) {
                            list.add(session)
                        }
                    }
                }
            }
        }
        return detectedSchedule
    }
}
