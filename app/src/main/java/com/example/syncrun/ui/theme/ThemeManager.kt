package com.example.syncrun.ui.theme

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

enum class AppLanguage { EN, IN }

object AppSettings {
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode = _isDarkMode.asStateFlow()

    private val _language = MutableStateFlow(AppLanguage.EN)
    val language = _language.asStateFlow()

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun setLanguage(language: AppLanguage, context: Context) {
        _language.value = language
        updateLocale(context, language)
    }

    private fun updateLocale(context: Context, language: AppLanguage) {
        val locale = if (language == AppLanguage.IN) Locale("in") else Locale("en")
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
