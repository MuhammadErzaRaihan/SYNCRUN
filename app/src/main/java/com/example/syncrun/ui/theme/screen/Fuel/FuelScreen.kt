package com.example.syncrun.ui.theme.screen.fuel

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun FuelScreen(
    viewModel: FuelViewModel = viewModel()
) {
    val currentNavMenu by viewModel.currentNavMenu.collectAsState()
    val spentBudget by viewModel.spentBudget.collectAsState()
    val dailyBudget by viewModel.dailyBudget.collectAsState()
    val caloriesConsumed by viewModel.caloriesConsumed.collectAsState()
    val caloriesTarget by viewModel.caloriesTarget.collectAsState()
    val macros by viewModel.macros.collectAsState()
    val meals by viewModel.meals.collectAsState()

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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // --- HEADER ---
            item {
                Column {
                    Text("BUDGET-OPTIMIZED NUTRITION", color = TextGray, fontSize = 10.sp, letterSpacing = 1.sp)
                    Text("FUEL & RECOVERY", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }

            // --- KARTU SUMMARY (BUDGET & CALORIES) ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SummaryCard(
                        modifier = Modifier.weight(1f),
                        title = "BUDGET SPENT",
                        value = "Rp ${spentBudget / 1000}k", // Format ke "k" (ribuan)
                        target = "/ Rp ${dailyBudget / 1000}k",
                        icon = Icons.Default.Savings,
                        accentColor = YellowAccent
                    )
                    SummaryCard(
                        modifier = Modifier.weight(1f),
                        title = "CALORIES",
                        value = "$caloriesConsumed",
                        target = "/ $caloriesTarget kcal",
                        icon = Icons.Default.LocalFireDepartment,
                        accentColor = CyanAccent
                    )
                }
            }

            // --- MACRONUTRIENTS PROGRESS ---
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(CardBackground)
                        .padding(20.dp)
                ) {
                    Text("MACRONUTRIENTS", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        macros.forEach { macro ->
                            MacroItem(macro)
                        }
                    }
                }
            }

            // --- DAFTAR MAKANAN HARI INI ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("TODAY'S MEALS", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { /* Aksi Tambah Makanan */ }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Add Meal", tint = YellowAccent)
                    }
                }
            }

            items(meals) { meal ->
                MealCard(meal)
            }

            // --- AI SUGGESTION BOX ---
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1E2F38))
                        .border(1.dp, CyanAccent.copy(alpha=0.5f), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("AI SUGGESTION", color = CyanAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "You have Rp 18k left. Grab a banana and 2 boiled eggs for dinner to hit your protein goal!",
                            color = Color.White,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

// ==========================================
// KOMPONEN PENDUKUNG
// ==========================================

@Composable
fun SummaryCard(modifier: Modifier, title: String, value: String, target: String, icon: ImageVector, accentColor: Color) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(title, color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(value, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(target, color = TextGray, fontSize = 12.sp)
    }
}

@Composable
fun MacroItem(macro: Macro) {
    val progress = macro.current.toFloat() / macro.target.toFloat()

    // Warna progres berdasarkan jenis makro
    val progressColor = when (macro.name) {
        "CARBS" -> YellowAccent
        "PROTEIN" -> CyanAccent
        else -> Color(0xFFFF5252) // Merah untuk Fats
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center) {
            // Latar belakang lingkaran
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.size(60.dp),
                color = DarkBackground,
                strokeWidth = 6.dp
            )
            // Progress utama
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(60.dp),
                color = progressColor,
                strokeWidth = 6.dp
            )
            Text("${macro.current}g", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(macro.name, color = TextGray, fontSize = 10.sp)
    }
}

@Composable
fun MealCard(meal: Meal) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ikon Bulat
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(DarkBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Restaurant, contentDescription = null, tint = YellowAccent, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))

        // Teks Utama
        Column(modifier = Modifier.weight(1f)) {
            Text(meal.type, color = YellowAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(meal.name, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }

        // Teks Kanan (Kalori & Harga)
        Column(horizontalAlignment = Alignment.End) {
            Text("${meal.calories} kcal", color = CyanAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text("Rp ${meal.price / 1000}k", color = TextGray, fontSize = 12.sp)
        }
    }
}

// --- PREVIEW ---
@Preview(showSystemUi = true)
@Composable
fun FuelScreenPreview() {
    FuelScreen(viewModel = FuelViewModel())
}