package com.example.syncrun.ui.theme.screen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun CalendarScreen(
    viewModel: CalendarViewModel = viewModel()
) {
    val currentNavMenu by viewModel.currentNavMenu.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val schedule by viewModel.schedule.collectAsState()

    val todaysWorkout = schedule[selectedDate] ?: emptyList()

    Scaffold(
        bottomBar = {
            SyncRunBottomBar(
                currentRoute = currentNavMenu,
                onItemClick = { viewModel.updateNavMenu(it) },
                onFabClick = { /* Aksi AI Coach */ }
            )
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // --- HEADER BULAN & TAHUN ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Month", tint = Color.White)
                Text(
                    text = "OCTOBER 2026",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Icon(Icons.Default.ChevronRight, contentDescription = "Next Month", tint = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- NAMA HARI (SUN, MON, TUE...) ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
                daysOfWeek.forEach { day ->
                    Text(
                        text = day,
                        color = TextGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- GRID TANGGAL KALENDER ---
            // Kita pakai 7 kolom agar persis kalender
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // Menambahkan Padding kosong karena 1 Oktober 2026 adalah hari Kamis (index ke-4)
                items(4) {
                    Box(modifier = Modifier.size(48.dp)) // Tempat kosong
                }

                // Menampilkan tanggal 1 sampai 31
                items(viewModel.calendarDays) { date ->
                    val hasWorkout = schedule.containsKey(date)
                    val workoutType = schedule[date]?.firstOrNull()?.type ?: ""

                    CalendarDayItem(
                        date = date,
                        isSelected = selectedDate == date,
                        hasWorkout = hasWorkout,
                        workoutType = workoutType,
                        onClick = { viewModel.selectDate(date) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- KARTU JADWAL LATIHAN DI BAWAH KALENDER ---
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (todaysWorkout.isEmpty()) {
                    item {
                        Text("No schedule for this day.", color = TextGray)
                    }
                } else {
                    items(todaysWorkout) { workout ->
                        WorkoutDetailCard(workout)
                    }
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}

// ==========================================
// KOMPONEN PENDUKUNG
// ==========================================

@Composable
fun CalendarDayItem(
    date: Int,
    isSelected: Boolean,
    hasWorkout: Boolean,
    workoutType: String,
    onClick: () -> Unit
) {
    // Menentukan warna titik kecil (dot) di bawah tanggal berdasarkan jenis latihan
    val dotColor = when (workoutType) {
        "REST" -> Color(0xFFB388FF) // Ungu/Biru
        "STRENGTH" -> Color(0xFFFF5252) // Merah
        "INTERVAL", "LONG RUN" -> CyanAccent
        "EASY RUN" -> YellowAccent
        else -> Color.Transparent
    }

    Column(
        modifier = Modifier
            .padding(4.dp)
            .size(44.dp)
            .clip(CircleShape)
            // Jika tanggal dipilih, backgroundnya kuning cerah, kalau tidak transparan
            .background(if (isSelected) YellowAccent else Color.Transparent)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Teks Tanggal
        Text(
            text = date.toString(),
            color = if (isSelected) Color.Black else Color.White,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Titik penanda jadwal latihan
        if (hasWorkout && !isSelected) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
        } else if (hasWorkout && isSelected) {
            // Jika hari tersebut sedang dipilih, titik penanda diubah warnanya jadi gelap agar kelihatan di atas warna kuning
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
            )
        }
    }
}

@Composable
fun WorkoutDetailCard(workout: WorkoutSession) {
    // Menentukan Icon dan Warna berdasarkan Tipe Latihan
    val (icon, iconTint) = when (workout.type) {
        "REST" -> Icons.Default.NightlightRound to Color(0xFFB388FF)
        "STRENGTH" -> Icons.Default.FitnessCenter to Color(0xFFFF5252)
        "INTERVAL", "LONG RUN" -> Icons.Default.Timer to CyanAccent
        else -> Icons.Default.DirectionsRun to YellowAccent
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = if (workout.isCompleted) Color(0xFF2E7D32) else CardBackground,
                shape = RoundedCornerShape(16.dp)
            )
            .background(CardBackground)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(iconTint.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(workout.type, color = iconTint, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            }

            if (workout.isCompleted) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Completed", tint = Color(0xFF4CAF50), modifier = Modifier.size(20.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(workout.title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        if (workout.type != "REST") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                workout.distance?.let { WorkoutMetric(label = "DISTANCE", value = it) }
                workout.duration?.let { WorkoutMetric(label = "DURATION", value = it) }
                workout.pace?.let { WorkoutMetric(label = "PACE", value = it) }
            }
        } else {
            Text("Take a break and let your muscles recover.", color = TextGray, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { /* Buka detail latihan */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (workout.isCompleted) Color(0xFF1B5E20) else Color(0xFF2B2D36)
            )
        ) {
            Text(
                if (workout.isCompleted) "VIEW RESULTS" else "START WORKOUT",
                color = if (workout.isCompleted) Color.White else CyanAccent,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun WorkoutMetric(label: String, value: String) {
    Column {
        Text(label, color = TextGray, fontSize = 10.sp, letterSpacing = 0.5.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

// --- PREVIEW ---
@Preview(showSystemUi = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen(viewModel = CalendarViewModel())
}