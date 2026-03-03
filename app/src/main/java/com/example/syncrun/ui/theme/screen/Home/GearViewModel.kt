package com.example.syncrun.ui.theme.screen.Home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.syncrun.ui.theme.component.NavMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// Data class harus ada di luar kelas viewmodel (atau di file terpisah) agar bisa dipakai luas
data class GearProduct(
    val category: String,
    val title: String,
    val rating: String,
    val price: String,
    val headerColor: Color,
    val icon: ImageVector
)

class GearViewModel : ViewModel() {
    private val _currentNavMenu = MutableStateFlow(NavMenu.HOME)
    val currentNavMenu = _currentNavMenu.asStateFlow()

    private val _products = MutableStateFlow(
        listOf(
            GearProduct("RUNNING SHOES", "Nike Air Zoom Pegasus 40", "4.8", "Rp 1.899.000", Color(0xFFFF4081), Icons.Default.DirectionsRun),
            GearProduct("RECOVERY TOOLS", "Foam Roller - Muscle Recovery", "4.9", "Rp 185.000", Color(0xFF00B0FF), Icons.Default.TrackChanges),
            GearProduct("APPAREL", "Running Compression Socks", "4.6", "Rp 125.000", Color(0xFFFFEA00), Icons.Default.Checkroom),
            GearProduct("NUTRITION", "Energy Gel Pack (12pcs)", "4.7", "Rp 220.000", Color(0xFFB388FF), Icons.Default.FlashOn),
            GearProduct("TECH", "GPS Running Watch", "4.8", "Rp 2.450.000", Color(0xFF37474F), Icons.Default.Watch),
            GearProduct("SAFETY", "Reflective Safety Vest", "4.5", "Rp 85.000", Color(0xFFFF6D00), Icons.Default.HealthAndSafety)
        )
    )
    val products = _products.asStateFlow()

    fun updateNavMenu(menu: NavMenu) {
        _currentNavMenu.value = menu
    }
}