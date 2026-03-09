package com.example.syncrun.ui.theme.screen.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.syncrun.R
import com.example.syncrun.ui.theme.AppLanguage
import com.example.syncrun.ui.theme.AppSettings

private val CyanAccent = Color(0xFF00E5FF)
private val TextGray = Color(0xFF9E9E9E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val isDarkMode by AppSettings.isDarkMode.collectAsState()
    val language by AppSettings.language.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        stringResource(R.string.settings_title), 
                        color = MaterialTheme.colorScheme.onBackground, 
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, 
                            contentDescription = "Back", 
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // --- DARK MODE TOGGLE ---
            SettingItem(
                icon = Icons.Default.DarkMode,
                title = stringResource(R.string.dark_mode_label),
                trailing = {
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { AppSettings.toggleDarkMode() },
                        colors = SwitchDefaults.colors(checkedThumbColor = CyanAccent)
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- LANGUAGE SELECTION ---
            var showLanguageDialog by remember { mutableStateOf(false) }

            SettingItem(
                icon = Icons.Default.Language,
                title = stringResource(R.string.language_label),
                subtitle = if (language == AppLanguage.EN) "English" else "Bahasa Indonesia",
                onClick = { showLanguageDialog = true }
            )

            if (showLanguageDialog) {
                AlertDialog(
                    onDismissRequest = { showLanguageDialog = false },
                    containerColor = MaterialTheme.colorScheme.surface,
                    title = { Text(stringResource(R.string.select_language), color = MaterialTheme.colorScheme.onSurface) },
                    text = {
                        Column {
                            LanguageOption("English", language == AppLanguage.EN) {
                                AppSettings.setLanguage(AppLanguage.EN, context)
                                showLanguageDialog = false
                            }
                            LanguageOption("Bahasa Indonesia", language == AppLanguage.IN) {
                                AppSettings.setLanguage(AppLanguage.IN, context)
                                showLanguageDialog = false
                            }
                        }
                    },
                    confirmButton = {}
                )
            }
        }
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            if (subtitle != null) {
                Text(subtitle, color = TextGray, fontSize = 12.sp)
            }
        }
        if (trailing != null) {
            trailing()
        } else if (onClick != null) {
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextGray)
        }
    }
}

@Composable
fun LanguageOption(label: String, isSelected: Boolean, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = CyanAccent)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
    }
}
