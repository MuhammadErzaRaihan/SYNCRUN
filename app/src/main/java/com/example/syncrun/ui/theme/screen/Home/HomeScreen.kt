package com.example.syncrun.ui.theme.screen.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.syncrun.R
import com.example.syncrun.ui.theme.SYNCRUNTheme

// --- TEMA WARNA LOKAL ---
private val YellowAccent = Color(0xFFC6FF00)
private val CyanAccent = Color(0xFF00E5FF)
private val TextGray = Color(0xFF9E9E9E)
private val PinkGradient = Brush.horizontalGradient(listOf(Color(0xFFFF5252), Color(0xFFFF1744)))

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToRoute: (String) -> Unit = {}
) {
    val currentWeight by viewModel.currentWeight.collectAsState()
    val totalKm by viewModel.totalKm.collectAsState()
    val targetWeight by viewModel.targetWeight.collectAsState()

    HomeContent(
        currentWeight = currentWeight,
        totalKm = totalKm,
        targetWeight = targetWeight,
        onNavigateToRoute = onNavigateToRoute
    )
}

@Composable
fun HomeContent(
    currentWeight: String,
    totalKm: String,
    targetWeight: String,
    onNavigateToRoute: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { HomeHeader() }
            item {
                StatsRow(
                    currentWeight = currentWeight,
                    totalKm = totalKm,
                    targetWeight = targetWeight
                )
            }
            item { CheckInBanner() }
            item { TodaySessionCard() }
            item { ThisWeekSection() }
            item { QuickActionsSection(onNavigateToRoute) }
            item { RunningAcademySection() }
            
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(stringResource(R.string.good_evening), color = TextGray, fontSize = 10.sp, letterSpacing = 1.sp)
            Text("ez", color = YellowAccent, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface)
                .clickable { /* Buka Profile */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun StatsRow(currentWeight: String, totalKm: String, targetWeight: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(modifier = Modifier.weight(1f), value = currentWeight, label = stringResource(R.string.current_kg), valueColor = YellowAccent)
        StatCard(modifier = Modifier.weight(1f), value = totalKm, label = stringResource(R.string.total_km), valueColor = CyanAccent)
        StatCard(modifier = Modifier.weight(1f), value = targetWeight, label = stringResource(R.string.target_kg), valueColor = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun StatCard(modifier: Modifier, value: String, label: String, valueColor: Color) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, color = valueColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, color = TextGray, fontSize = 10.sp)
    }
}

@Composable
fun CheckInBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(PinkGradient)
            .clickable { /* Aksi Check-in */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.MonitorHeart, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.start_your_day), color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.daily_checkin_desc), color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.White)
    }
}

@Composable
fun TodaySessionCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, YellowAccent.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(YellowAccent))
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.todays_session), color = TextGray, fontSize = 10.sp, letterSpacing = 1.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("😴", fontSize = 40.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Text(stringResource(R.string.rest_day), color = MaterialTheme.colorScheme.onSurface, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.background).padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = YellowAccent, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.afternoon_clear_tip), color = TextGray, fontSize = 11.sp)
        }
    }
}

@Composable
fun ThisWeekSection() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CalendarToday, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.this_week), color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text(stringResource(R.string.training_cycle), color = TextGray, fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        ScheduleItem(stringResource(R.string.day_sun), "Long Run: 10km", "🏃")
        ScheduleItem(stringResource(R.string.day_mon), stringResource(R.string.rest_day), "😴")
        ScheduleItem(stringResource(R.string.day_tue), "Easy Run: 5km", "🏃")
        ScheduleItem("THU", "LOWER BODY STRENGTH...", "💪")
        ScheduleItem("FRI", "Easy Run: 5km", "🏃")
        ScheduleItem("SAT", "Rest Day", "😴")
    }
}

@Composable
fun ScheduleItem(day: String, title: String, emoji: String) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { }.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(emoji, fontSize = 24.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(day, color = TextGray, fontSize = 10.sp)
            Text(title, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextGray, modifier = Modifier.size(20.dp))
    }
}

@Composable
fun QuickActionsSection(onNavigateToRoute: (String) -> Unit = {}) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionButton(modifier = Modifier.weight(1f), icon = Icons.Default.Restaurant, label = stringResource(R.string.nutrition))
            QuickActionButton(modifier = Modifier.weight(1f), icon = Icons.Default.CameraAlt, label = stringResource(R.string.log_run))
            QuickActionButton(modifier = Modifier.weight(1f), icon = Icons.Default.TrendingUp, label = stringResource(R.string.progress))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionButton(modifier = Modifier.weight(1f), icon = Icons.Default.PlayCircleOutline, label = stringResource(R.string.workouts))
            QuickActionButton(
                modifier = Modifier.weight(1f), 
                icon = Icons.Default.ShoppingBag, 
                label = stringResource(R.string.gear_store),
                onClick = { onNavigateToRoute("gear") }
            )
        }
    }
}

@Composable
fun QuickActionButton(
    modifier: Modifier, 
    icon: ImageVector, 
    label: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = label, tint = TextGray, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, color = TextGray, fontSize = 10.sp)
    }
}

@Composable
fun RunningAcademySection() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(YellowAccent))
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.running_academy), color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text(stringResource(R.string.learn_and_grow), color = TextGray, fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            item { AcademyCard("5 Tips for Your First Half-Marathon", "Master the fundamentals...", Color(0xFFFFB74D)) }
            item { AcademyCard("Choosing the Right Shoes", "Find the perfect fit...", Color(0xFF81C784)) }
        }
    }
}

@Composable
fun AcademyCard(title: String, subtitle: String, color: Color) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { }
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(color))
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitle, color = TextGray, fontSize = 10.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SYNCRUNTheme {
        HomeContent(
            currentWeight = "75",
            totalKm = "120.5",
            targetWeight = "70"
        )
    }
}
