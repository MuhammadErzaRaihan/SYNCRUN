package com.example.syncrun.ui.theme.screen.setup

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncrun.data.ScheduleRepository
import com.example.syncrun.ui.theme.screen.calendar.WorkoutSession
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Setup3ViewModel : ViewModel() {
    private val _isUploaded = MutableStateFlow(false)
    val isUploaded = _isUploaded.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    private val _detectedSchedule = MutableStateFlow<Map<Int, List<WorkoutSession>>>(emptyMap())
    val detectedSchedule = _detectedSchedule.asStateFlow()

    private val _showVerificationDialog = MutableStateFlow(false)
    val showVerificationDialog = _showVerificationDialog.asStateFlow()

    private val _uploadedCount = MutableStateFlow(0)
    val uploadedCount = _uploadedCount.asStateFlow()

    // Use lazy initialization to avoid MlKitContext issues during Preview instantiation
    private val recognizer by lazy {
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    /**
     * Memproses beberapa gambar sekaligus (maksimal 3)
     */
    fun processKrsImages(context: Context, uris: List<Uri>) {
        if (uris.isEmpty()) return
        
        // Batasi maksimal 3 gambar
        val limitedUris = uris.take(3)
        _uploadedCount.value = limitedUris.size
        
        viewModelScope.launch {
            _isProcessing.value = true
            val combinedResults = mutableMapOf<Int, MutableList<WorkoutSession>>()
            
            try {
                withContext(Dispatchers.IO) {
                    for (uri in limitedUris) {
                        val image = InputImage.fromFilePath(context, uri)
                        // Menggunakan Tasks.await untuk sinkronisasi di dalam coroutine IO
                        val visionText = Tasks.await(recognizer.process(image))
                        val result = KrsParser.parseTextToSchedule(visionText.text)
                        
                        // Menggabungkan hasil OCR dari setiap gambar
                        result.forEach { (date, sessions) ->
                            if (!combinedResults.containsKey(date)) {
                                combinedResults[date] = mutableListOf()
                            }
                            // Tambahkan hanya jika belum ada (menghindari duplikasi antar screenshot)
                            sessions.forEach { newSession ->
                                val isDuplicate = combinedResults[date]?.any { 
                                    it.title == newSession.title && it.duration == newSession.duration 
                                } ?: false
                                
                                if (!isDuplicate) {
                                    combinedResults[date]?.add(newSession)
                                }
                            }
                        }
                    }
                }
                
                _detectedSchedule.value = combinedResults
                _isProcessing.value = false
                _showVerificationDialog.value = true
                
            } catch (e: Exception) {
                _isProcessing.value = false
            }
        }
    }

    fun confirmSchedule() {
        ScheduleRepository.updateSchedule(_detectedSchedule.value)
        _isUploaded.value = true
        _showVerificationDialog.value = false
    }

    fun dismissDialog() {
        _showVerificationDialog.value = false
        _uploadedCount.value = 0
        _detectedSchedule.value = emptyMap()
    }

    fun simulateUpload() {
        _isUploaded.value = !_isUploaded.value
    }
}
