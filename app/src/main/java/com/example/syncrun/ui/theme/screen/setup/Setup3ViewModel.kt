package com.example.syncrun.ui.theme.screen.setup

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.syncrun.ui.theme.repository.ScheduleRepository
import com.example.syncrun.ui.theme.screen.calendar.WorkoutSession
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class Setup3ViewModel : ViewModel() {

    private val _isUploaded = MutableStateFlow(false)
    val isUploaded: StateFlow<Boolean> = _isUploaded.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // State untuk menyimpan hasil OCR
    private val _agendaTitle = MutableStateFlow("")
    val agendaTitle: StateFlow<String> = _agendaTitle.asStateFlow()

    private val _agendaDate = MutableStateFlow("")
    val agendaDate: StateFlow<String> = _agendaDate.asStateFlow()

    private val _agendaTime = MutableStateFlow("")
    val agendaTime: StateFlow<String> = _agendaTime.asStateFlow()

    fun runOCR(context: Context, imageUri: Uri) {
        _isLoading.value = true

        try {
            // Lebih aman menggunakan fromFilePath langsung dari Uri daripada konversi Bitmap manual
            val image = InputImage.fromFilePath(context, imageUri)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    _isLoading.value = false
                    _isUploaded.value = true
                    processExtractedText(visionText)
                }
                .addOnFailureListener { e ->
                    _isLoading.value = false
                    _isUploaded.value = false
                    Toast.makeText(context, "Gagal membaca gambar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            _isLoading.value = false
            Toast.makeText(context, "Error memuat gambar", Toast.LENGTH_SHORT).show()
        }
    }

    // Perhatikan: Menggunakan com.google.mlkit.vision.text.Text, BUKAN material3.Text
    private fun processExtractedText(result: com.google.mlkit.vision.text.Text) {
        val fullText = result.text

        val dateRegex = Regex("(\\d{1,2}[/.-]\\d{1,2}[/.-]\\d{2,4})|(\\d{1,2}\\s+(Januari|Februari|Maret|April|Mei|Juni|Juli|Agustus|September|Oktober|November|Desember)\\s+\\d{4})", RegexOption.IGNORE_CASE)
        val timeRegex = Regex("(\\d{2}[:.]\\d{2})")

        val detectedDate = dateRegex.find(fullText)?.value ?: "Tanggal tidak ditemukan"
        val detectedTime = timeRegex.find(fullText)?.value ?: "Waktu tidak ditemukan"

        var detectedTitle = "Judul tidak ditemukan"
        for (block in result.textBlocks) {
            val text = block.text
            if (!dateRegex.containsMatchIn(text) && !timeRegex.containsMatchIn(text) && text.length > 3) {
                detectedTitle = text
                break
            }
        }

        // Update state UI
        _agendaTitle.value = detectedTitle
        _agendaDate.value = detectedDate
        _agendaTime.value = detectedTime
    }

    fun saveOcrToCalendar() {
        // Asumsi format _agendaDate.value adalah "12/10/2026" atau "12 Oktober 2026"
        val dateString = _agendaDate.value
        val title = _agendaTitle.value
        val time = _agendaTime.value

        // Ambil 2 angka pertama sebagai tanggal (contoh: "12")
        val dayInt = dateString.take(2).trim().toIntOrNull()

        if (dayInt != null && title.isNotBlank()) {
            val newClassSession = WorkoutSession(
                type = "CLASS",
                title = title,
                time = time
            )
            // Simpan ke Repository!
            ScheduleRepository.addSchedule(date = dayInt, session = newClassSession)
        }
    }
}