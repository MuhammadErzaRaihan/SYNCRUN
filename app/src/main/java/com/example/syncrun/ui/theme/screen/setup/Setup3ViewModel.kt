package com.example.syncrun.ui.theme.screen.setup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class Setup3ViewModel : ViewModel() {
    private val _isUploaded = MutableStateFlow(false)
    val isUploaded = _isUploaded.asStateFlow()

    fun simulateUpload() {
        _isUploaded.value = !_isUploaded.value // Toggle (hanya untuk prototype)
    }
}
