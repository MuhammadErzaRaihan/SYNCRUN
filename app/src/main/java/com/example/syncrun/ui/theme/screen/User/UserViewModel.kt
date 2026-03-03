package com.example.syncrun.ui.theme.screen.user

import androidx.lifecycle.ViewModel
import com.example.syncrun.ui.theme.component.NavMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {
    // Navigasi Bottom Bar (Default ke PROFILE)
    private val _currentNavMenu = MutableStateFlow(NavMenu.PROFILE)
    val currentNavMenu = _currentNavMenu.asStateFlow()

    // Data User
    private val _userName = MutableStateFlow("Erza Raihan")
    val userName = _userName.asStateFlow()

    private val _userEmail = MutableStateFlow("erzaraihan@gmail.com")
    val userEmail = _userEmail.asStateFlow()

    // Statistik User
    private val _totalRuns = MutableStateFlow("14")
    val totalRuns = _totalRuns.asStateFlow()

    private val _totalDistance = MutableStateFlow("42.5 km")
    val totalDistance = _totalDistance.asStateFlow()

    private val _activeGoal = MutableStateFlow("5K Race")
    val activeGoal = _activeGoal.asStateFlow()

    fun updateNavMenu(menu: NavMenu) {
        _currentNavMenu.value = menu
    }
}