package com.example.syncrun.ui.theme.screen.calendar

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.syncrun.R
import com.example.syncrun.ui.theme.SYNCRUNTheme
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
    viewModel: CalendarViewModel = viewModel(),
    onNavigateToRoute: (String) -> Unit = {}
) {
    val currentNavMenu by viewModel.currentNavMenu.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val schedule by viewModel.schedule.collectAsState()
    val showAddDialog by viewModel.showAddEventDialog.collectAsState()

    CalendarContent(
        currentNavMenu = currentNavMenu,
        selectedDate = selectedDate,
        schedule = schedule,
        showAddDialog = showAddDialog,
        calendarDays = viewModel.calendarDays,
        onSelectDate = { viewModel.selectDate(it) },
        onToggleAddDialog = { viewModel.toggleAddEventDialog(it) },
        onConfirmAddEvent = { title, time -> viewModel.addNewClass(title, time) },
        onNavigateToRoute = onNavigateToRoute,
        onUpdateNavMenu = { viewModel.updateNavMenu(it) }
    )
}

@Composable
fun CalendarContent(
    currentNavMenu: NavMenu,
    selectedDate: Int,
    schedule: Map<Int, List<WorkoutSession>>,
    showAddDialog: Boolean,
    calendarDays: List<Int>,
    onSelectDate: (Int) -> Unit,
    onToggleAddDialog: (Boolean) -> Unit,
    onConfirmAddEvent: (String, String) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    onUpdateNavMenu: (NavMenu) -> Unit
) {
    val todaysWorkout = schedule[selectedDate] ?: emptyList()

    if (showAddDialog) {
        AddEventDialog(
            onDismiss = { onToggleAddDialog(false) },
            onConfirm = { title, time -> onConfirmAddEvent(title, time) }
        )
    }

    Scaffold(
        bottomBar = {
            SyncRunBottomBar(
                currentRoute = currentNavMenu,
                onItemClick = { menu ->
                    onUpdateNavMenu(menu)
                    onNavigateToRoute(menu.name.lowercase())
                },
                onFabClick = { /* Aksi AI Coach */ }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onToggleAddDialog(true) },
                containerColor = YellowAccent,
                contentColor = Color.Black,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Event")
            }
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
                    text = stringResource(R.string.calendar_month_year),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Icon(Icons.Default.ChevronRight, contentDescription = "Next Month", tint = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- NAMA HARI ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val daysOfWeek = listOf(
                    R.string.day_sun, R.string.day_mon, R.string.day_tue,
                    R.string.day_wed, R.string.day_thu, R.string.day_fri, R.string.day_sat
                )
                daysOfWeek.forEach { dayRes ->
                    Text(
                        text = stringResource(dayRes),
                        color = TextGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- GRID TANGGAL ---
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Feb 2026 mulai hari Minggu (Index 0)
                items(calendarDays) { date ->
                    val hasWorkout = schedule.containsKey(date)
                    val workoutType = schedule[date]?.firstOrNull()?.type ?: ""

                    CalendarDayItem(
                        date = date,
                        isSelected = selectedDate == date,
                        hasWorkout = hasWorkout,
                        workoutType = workoutType,
                        onClick = { onSelectDate(date) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- KARTU JADWAL ---
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (todaysWorkout.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp), contentAlignment = Alignment.Center) {
                            Text(stringResource(R.string.no_schedule), color = TextGray)
                        }
                    }
                } else {
                    items(todaysWorkout) { workout ->
                        WorkoutDetailCard(workout)
                    }
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
fun AddEventDialog(onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CardBackground,
        title = { Text(stringResource(R.string.add_event_title), color = Color.White, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.course_name_label)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = CyanAccent,
                        unfocusedBorderColor = Color.DarkGray
                    )
                )
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text(stringResource(R.string.time_example_label)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = CyanAccent,
                        unfocusedBorderColor = Color.DarkGray
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (title.isNotEmpty()) onConfirm(title, time) },
                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
            ) {
                Text(stringResource(R.string.save_button), color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_button), color = Color.Gray)
            }
        }
    )
}

@Composable
fun CalendarDayItem(
    date: Int,
    isSelected: Boolean,
    hasWorkout: Boolean,
    workoutType: String,
    onClick: () -> Unit
) {
    val dotColor = when (workoutType) {
        "CLASS" -> Color(0xFF4CAF50)
        "REST" -> Color(0xFFB388FF)
        "STRENGTH" -> Color(0xFFFF5252)
        "INTERVAL", "LONG RUN" -> CyanAccent
        "EASY RUN" -> YellowAccent
        else -> Color.Transparent
    }

    Column(
        modifier = Modifier
            .padding(4.dp)
            .size(44.dp)
            .clip(CircleShape)
            .background(if (isSelected) YellowAccent else Color.Transparent)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = date.toString(),
            color = if (isSelected) Color.Black else Color.White,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
        if (hasWorkout && !isSelected) {
            Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(dotColor))
        }
    }
}

@Composable
fun WorkoutDetailCard(workout: WorkoutSession) {
    val (icon, iconTint, typeLabel) = when (workout.type) {
        "CLASS" -> Triple(Icons.Default.School, Color(0xFF4CAF50), stringResource(R.string.workout_type_class))
        "REST" -> Triple(Icons.Default.NightlightRound, Color(0xFFB388FF), stringResource(R.string.workout_type_rest))
        "STRENGTH" -> Triple(Icons.Default.FitnessCenter, Color(0xFFFF5252), stringResource(R.string.workout_type_strength))
        "INTERVAL" -> Triple(Icons.Default.Timer, CyanAccent, stringResource(R.string.workout_type_interval))
        "LONG RUN" -> Triple(Icons.Default.Timer, CyanAccent, stringResource(R.string.workout_type_long_run))
        else -> Triple(Icons.Default.DirectionsRun, YellowAccent, stringResource(R.string.workout_type_easy_run))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground)
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(32.dp).clip(CircleShape).background(iconTint.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(typeLabel, color = iconTint, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(workout.title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        val durationText = if (workout.type == "CLASS") {
            stringResource(R.string.time_prefix, workout.duration ?: "")
        } else {
            workout.duration ?: ""
        }
        Text(durationText, color = TextGray, fontSize = 14.sp)
    }
}

@Composable
fun WorkoutMetric(label: String, value: String) {
    Column {
        Text(label, color = TextGray, fontSize = 10.sp)
        Text(value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    val sampleSchedule = mapOf(
        15 to listOf(
            WorkoutSession(type = "CLASS", title = "Pemrograman Mobile", duration = "08:00 - 10:30"),
            WorkoutSession(type = "EASY RUN", title = "Morning Run", duration = "30 min")
        ),
        16 to listOf(
            WorkoutSession(type = "REST", title = "Rest Day", duration = "-")
        )
    )

    SYNCRUNTheme {
        CalendarContent(
            currentNavMenu = NavMenu.CALENDAR,
            selectedDate = 15,
            schedule = sampleSchedule,
            showAddDialog = false,
            calendarDays = (1..31).toList(),
            onSelectDate = {},
            onToggleAddDialog = {},
            onConfirmAddEvent = { _, _ -> },
            onNavigateToRoute = {},
            onUpdateNavMenu = {}
        )
    }
}
