package com.example.syncrun.ui.theme.screen.setup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Setup2ViewModel : ViewModel() {
    private val _easyPace = MutableStateFlow("")
    val easyPace = _easyPace.asStateFlow()

    private val _isCalculated = MutableStateFlow(false)
    val isCalculated = _isCalculated.asStateFlow()

    // State untuk menyimpan hasil kalkulasi
    private val _intervalPace = MutableStateFlow("--:--")
    val intervalPace = _intervalPace.asStateFlow()

    private val _longRunPace = MutableStateFlow("--:--")
    val longRunPace = _longRunPace.asStateFlow()

    fun updatePace(newValue: String) {
        // Logika otomatis menambahkan :00 jika input hanya angka (single digit 1-9 atau >= 10)
        val processedValue = if (newValue.isNotEmpty() && !newValue.contains(":") && newValue.all { it.isDigit() }) {
            "$newValue:00"
        } else {
            newValue
        }
        
        _easyPace.value = processedValue
        _isCalculated.value = false // Reset kalkulasi jika user mengganti angka
    }

    fun calculateAI() {
        val inputPace = _easyPace.value

        // Cek apakah format input valid (mengandung ":" seperti 06:00 atau 6:00)
        if (inputPace.contains(":")) {
            try {
                val parts = inputPace.split(":")
                val minutes = parts[0].toInt()
                val seconds = if (parts.size > 1 && parts[1].isNotEmpty()) parts[1].toInt() else 0

                // Ubah waktu ke total detik agar mudah dihitung
                val totalSeconds = (minutes * 60) + seconds

                // RUMUS PREDIKSI SEDERHANA (HEURISTIC)
                // Interval Pace: 1 menit 15 detik (75 detik) lebih CEPAT dari Easy Pace
                val intervalTotalSeconds = (totalSeconds - 75).coerceAtLeast(0)

                // Long Run Pace: 30 detik lebih LAMBAT dari Easy Pace
                val longRunTotalSeconds = totalSeconds + 30

                // Format kembali ke String "MM:SS"
                _intervalPace.value = formatSecondsToTime(intervalTotalSeconds)
                _longRunPace.value = formatSecondsToTime(longRunTotalSeconds)

                _isCalculated.value = true

            } catch (e: Exception) {
                // Jika input gagal di-parse, kembalikan ke format kosong
                _intervalPace.value = "Error"
                _longRunPace.value = "Error"
            }
        }
    }

    // Fungsi bantuan untuk mengubah detik menjadi format "MM:SS"
    private fun formatSecondsToTime(totalSecs: Int): String {
        val m = totalSecs / 60
        val s = totalSecs % 60
        return String.format("%02d:%02d", m, s)
    }
}
