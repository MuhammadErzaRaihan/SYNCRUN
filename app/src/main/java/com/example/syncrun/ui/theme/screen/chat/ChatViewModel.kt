package com.example.syncrun.ui.theme.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatMessage(
    val role: String, // "user" or "model"
    val message: String
)

class ChatViewModel : ViewModel() {
    // Sapaan awal tetap ditampilkan di UI
    private val _messages = MutableStateFlow<List<ChatMessage>>(listOf(
        ChatMessage("model", "Halo! Saya AI Coach SYNCRUN. Ada yang bisa saya bantu dengan jadwal lari atau nutrisi Anda hari ini?")
    ))
    val messages = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var apiKey = ""
    // Simpan session chat agar history otomatis dikelola oleh SDK
    private var chatSession: Chat? = null

    fun setApiKey(key: String) {
        apiKey = key
        if (apiKey.isNotBlank()) {
            // Gunakan model generasi terbaru (gemini-2.5-flash) untuk menghindari error 404
            val generativeModel = GenerativeModel(
                modelName = "gemini-2.5-flash",
                apiKey = apiKey
            )
            // Inisialisasi percakapan kosong agar SDK mengelola riwayatnya secara otomatis
            chatSession = generativeModel.startChat()
        }
    }

    fun sendMessage(userPrompt: String) {
        val chat = chatSession
        if (userPrompt.isBlank() || chat == null) return

        // Tambahkan pesan user ke UI
        _messages.value = _messages.value + ChatMessage("user", userPrompt)
        _isLoading.value = true

        viewModelScope.launch {
            try {
                // Mengirim pesan menggunakan sesi chat
                val response = chat.sendMessage(userPrompt)
                val responseText = response.text
                if (responseText != null) {
                    _messages.value = _messages.value + ChatMessage("model", responseText)
                }
            } catch (e: Exception) {
                val errorMessage = e.localizedMessage ?: "Terjadi kesalahan yang tidak diketahui"
                _messages.value = _messages.value + ChatMessage("model", "Pesan Error API: $errorMessage")
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}