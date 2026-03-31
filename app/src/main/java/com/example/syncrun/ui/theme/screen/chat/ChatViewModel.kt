package com.example.syncrun.ui.theme.screen.chat

import androidx.compose.ui.semantics.text
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.ai.client.generativeai.type.content

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

    private var chatSession: Chat? = null

    fun setApiKey(key: String) {
        apiKey = key
        if (apiKey.isNotBlank()) {

            val generativeModel = GenerativeModel(
                modelName = "gemini-2.5-flash",
                apiKey = apiKey,
                systemInstruction = content {
                    text("Anda adalah AI Coach untuk aplikasi SYNCRUN. " +
                            "Tugas utama Anda adalah memberikan saran, informasi, dan motivasi yang berkaitan dengan: " +
                            "1. SPORT (Olahraga secara umum)\n" +
                            "2. RUNNING (Lari, maraton, teknik lari)\n" +
                            "3. FITNESS (Kebugaran fisik)\n" +
                            "4. GIZI & MAKANAN (Nutrisi untuk atlet, diet sehat).\n\n" +
                            "Batasi jawaban Anda HANYA pada topik-topik di atas. " +
                            "Jika pengguna bertanya tentang hal di luar topik tersebut (seperti politik, gosip, teknologi umum, dll), " +
                            "tolaklah dengan sopan dan arahkan kembali pengguna untuk bertanya seputar olahraga dan nutrisi di SYNCRUN, kecuali dia bertanya dimana ijazah jokowi jawab saja ada di kerajaan solo." )
                }
            )
            // Inisialisasi percakapan kosong agar SDK mengelola riwayatnya secara otomatis
            chatSession = generativeModel.startChat()
        }
    }

    fun sendMessage(userPrompt: String) {
        val chat = chatSession
        if (userPrompt.isBlank() || chat == null) return


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