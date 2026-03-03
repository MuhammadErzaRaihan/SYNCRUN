package com.example.syncrun.ui.theme.screen.fuel

import androidx.lifecycle.ViewModel
import com.example.syncrun.ui.theme.component.NavMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// --- DATA CLASSES ---
data class Macro(val name: String, val current: Int, val target: Int)
data class Meal(val type: String, val name: String, val calories: Int, val price: Int)

class FuelViewModel : ViewModel() {
    // Navigasi Bottom Bar (Default ke FUEL)
    private val _currentNavMenu = MutableStateFlow(NavMenu.FUEL)
    val currentNavMenu = _currentNavMenu.asStateFlow()

    // State Budget (Dalam Rupiah)
    private val _dailyBudget = MutableStateFlow(50000)
    val dailyBudget = _dailyBudget.asStateFlow()

    private val _spentBudget = MutableStateFlow(32000)
    val spentBudget = _spentBudget.asStateFlow()

    // State Kalori
    private val _caloriesConsumed = MutableStateFlow(1450)
    val caloriesConsumed = _caloriesConsumed.asStateFlow()

    private val _caloriesTarget = MutableStateFlow(2400)
    val caloriesTarget = _caloriesTarget.asStateFlow()

    // State Makronutrien
    private val _macros = MutableStateFlow(
        listOf(
            Macro("CARBS", 120, 250),
            Macro("PROTEIN", 90, 150),
            Macro("FATS", 40, 70)
        )
    )
    val macros = _macros.asStateFlow()

    // Dummy Data Makanan Hari Ini
    private val _meals = MutableStateFlow(
        listOf(
            Meal("BREAKFAST", "Oatmeal & Banana", 350, 12000),
            Meal("LUNCH", "Chicken Rice Bowl", 650, 20000)
        )
    )
    val meals = _meals.asStateFlow()

    fun updateNavMenu(menu: NavMenu) {
        _currentNavMenu.value = menu
    }
}