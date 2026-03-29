package com.example.syncrun.ui.theme.screen.Home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncrun.data.AppDatabase
import com.example.syncrun.data.UserEntity
import com.example.syncrun.ui.theme.component.NavMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application, viewModelScope)
    private val userDao = db.userDao()

    val user: StateFlow<UserEntity?> = userDao.getFirstUser().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val currentWeight: StateFlow<String> = user.map { it?.currentWeight?.toString() ?: "0" }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "0"
    )

    val totalKm: StateFlow<String> = user.map { it?.totalKm?.toString() ?: "0.0" }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "0.0"
    )

    val targetWeight: StateFlow<String> = user.map { it?.targetWeight?.toString() ?: "0" }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "0"
    )

    private val _currentNavMenu = MutableStateFlow(NavMenu.HOME)
    val currentNavMenu = _currentNavMenu.asStateFlow()

    fun updateNavMenu(menu: NavMenu) {
        _currentNavMenu.value = menu
    }
}
