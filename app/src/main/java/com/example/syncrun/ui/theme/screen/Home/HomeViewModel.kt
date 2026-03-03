package com.example.syncrun.ui.theme.screen.Home

import androidx.lifecycle.ViewModel
import com.example.syncrun.ui.theme.component.NavMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    // State untuk Bottom Navigation Bar
    private val _currentNavMenu = MutableStateFlow(NavMenu.HOME)
    val currentNavMenu = _currentNavMenu.asStateFlow()

    // State untuk Statistik Pengguna
    private val _currentWeight = MutableStateFlow("111")
    val currentWeight = _currentWeight.asStateFlow()

    private val _totalKm = MutableStateFlow("0")
    val totalKm = _totalKm.asStateFlow()

    private val _targetWeight = MutableStateFlow("100")
    val targetWeight = _targetWeight.asStateFlow()

    fun updateNavMenu(menu: NavMenu) {
        _currentNavMenu.value = menu
    }
}