package com.example.syncrun.ui.theme.screen.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Import Bottom Bar
import com.example.syncrun.ui.theme.component.NavMenu
import com.example.syncrun.ui.theme.component.SyncRunBottomBar

// --- TEMA WARNA LOKAL ---
private val DarkBackground = Color(0xFF16171D)
private val CardBackground = Color(0xFF22232A)
private val YellowAccent = Color(0xFFC6FF00)
private val CyanAccent = Color(0xFF00E5FF)
private val TextGray = Color(0xFF9E9E9E)

@Composable
fun UserScreen(
    viewModel: UserViewModel = viewModel(),
    onLogoutClick: () -> Unit = {}
) {
    val currentNavMenu by viewModel.currentNavMenu.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userEmail by viewModel.userEmail.collectAsState()
    val totalRuns by viewModel.totalRuns.collectAsState()
    val totalDistance by viewModel.totalDistance.collectAsState()
    val activeGoal by viewModel.activeGoal.collectAsState()

    Scaffold(
        bottomBar = {
            SyncRunBottomBar(
                currentRoute = currentNavMenu,
                onItemClick = { viewModel.updateNavMenu(it) },
                onFabClick = { /* Buka AI Coach */ }
            )
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Membuat konten rata tengah
        ) {
            item { Spacer(modifier = Modifier.height(32.dp)) }

            // --- HEADER: FOTO PROFIL & NAMA (CENTERED) ---
            item {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(CardBackground)
                        .border(2.dp, CyanAccent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = TextGray, modifier = Modifier.size(50.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(userName, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(userEmail, color = TextGray, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(24.dp))
            }

            // --- KARTU STATISTIK ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatBox(modifier = Modifier.weight(1f), label = "RUNS", value = totalRuns, color = YellowAccent)
                    StatBox(modifier = Modifier.weight(1f), label = "DISTANCE", value = totalDistance, color = CyanAccent)
                    StatBox(modifier = Modifier.weight(1f), label = "GOAL", value = activeGoal, color = Color.White)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // --- MENU PENGATURAN ---
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(CardBackground)
                ) {
                    MenuRow(icon = Icons.Default.Edit, title = "Edit Profile")
                    HorizontalDivider(color = DarkBackground, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                    MenuRow(icon = Icons.Default.Flag, title = "My Goals")
                    HorizontalDivider(color = DarkBackground, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                    MenuRow(icon = Icons.Default.Settings, title = "Settings")
                    HorizontalDivider(color = DarkBackground, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                    MenuRow(icon = Icons.Default.HelpOutline, title = "Help & Support")
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // --- LOGOUT BUTTON ---
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFF1744).copy(alpha = 0.1f))
                        .border(1.dp, Color(0xFFFF1744), RoundedCornerShape(12.dp))
                        .clickable { onLogoutClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Logout, contentDescription = null, tint = Color(0xFFFF1744))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("LOGOUT", color = Color(0xFFFF1744), fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// ==========================================
// KOMPONEN PENDUKUNG
// ==========================================

@Composable
fun StatBox(modifier: Modifier, label: String, value: String, color: Color) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, color = TextGray, fontSize = 10.sp, letterSpacing = 0.5.sp)
    }
}

@Composable
fun MenuRow(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Aksi klik menu */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = TextGray, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextGray)
    }
}

// --- PREVIEW ---
@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = true)
@Composable
fun UserScreenPreview() {
    UserScreen(
        viewModel = UserViewModel(),
        onLogoutClick = {}
    )
}