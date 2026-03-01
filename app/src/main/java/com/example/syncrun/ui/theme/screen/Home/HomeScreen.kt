package com.example.syncrun.ui.theme.screen.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Import komponen Bottom Bar Anda
import com.example.syncrun.ui.theme.component.NavMenu
import com.example.syncrun.ui.theme.component.SyncRunBottomBar

// --- TEMA WARNA LOKAL ---
private val DarkBackground = Color(0xFF16171D)
private val CardBackground = Color(0xFF22232A)
private val YellowAccent = Color(0xFFC6FF00)
private val CyanAccent = Color(0xFF00E5FF)
private val TextGray = Color(0xFF9E9E9E)
private val PinkGradient = Brush.horizontalGradient(listOf(Color(0xFFFF5252), Color(0xFFFF1744)))

@Composable
fun HomeScreen() {
    var currentNavMenu by remember { mutableStateOf(NavMenu.HOME) }

    Scaffold(
        bottomBar = {
            SyncRunBottomBar(
                currentRoute = currentNavMenu,
                onItemClick = { currentNavMenu = it },
                onFabClick = { /* Buka AI Coach */ }
            )
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // 1. HEADER
            item { HomeHeader() }

            // 2. STATISTIK
            item { StatsRow() }

            // 3. DAILY CHECK-IN BANNER
            item { CheckInBanner() }

            // 4. TODAY'S SESSION
            item { TodaySessionCard() }

            // 5. THIS WEEK (JADWAL)
            item { ThisWeekSection() }

            // 6. QUICK ACTIONS (GRID BUTTONS)
            item { QuickActionsSection() }

            // 7. RUNNING ACADEMY (CAROUSEL)
            item { RunningAcademySection() }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

// ==========================================
// KOMPONEN-KOMPONEN PENYUSUN HOME SCREEN
// ==========================================

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("GOOD EVENING", color = TextGray, fontSize = 10.sp, letterSpacing = 1.sp)
            Text("e", color = YellowAccent, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CardBackground)
                .clickable { /* Buka Profile */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
        }
    }
}

@Composable
fun StatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(modifier = Modifier.weight(1f), value = "111", label = "CURRENT KG", valueColor = YellowAccent)
        StatCard(modifier = Modifier.weight(1f), value = "0", label = "TOTAL KM", valueColor = CyanAccent)
        StatCard(modifier = Modifier.weight(1f), value = "100", label = "TARGET KG", valueColor = Color.White)
    }
}

@Composable
fun StatCard(modifier: Modifier, value: String, label: String, valueColor: Color) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
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
            Text("START YOUR DAY", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text("Complete daily check-in", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
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
            .background(CardBackground)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(YellowAccent))
            Spacer(modifier = Modifier.width(8.dp))
            Text("TODAY'S SESSION", color = TextGray, fontSize = 10.sp, letterSpacing = 1.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("😴", fontSize = 40.sp) // Emoji Placeholder
            Spacer(modifier = Modifier.width(16.dp))
            Text("Rest Day", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(DarkBackground).padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = YellowAccent, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Since your afternoon schedule is clear, let's get moving!", color = TextGray, fontSize = 11.sp)
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
                Text("THIS WEEK", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text("TRAINING CYCLE", color = TextGray, fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Dummy List Jadwal
        ScheduleItem("SUN", "Long Run: 10km", "🏃")
        ScheduleItem("MON", "Rest Day", "😴")
        ScheduleItem("TUE", "Easy Run: 5km", "🏃")
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
            Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextGray, modifier = Modifier.size(20.dp))
    }
}

@Composable
fun QuickActionsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionButton(modifier = Modifier.weight(1f), icon = Icons.Default.Restaurant, label = "NUTRITION")
            QuickActionButton(modifier = Modifier.weight(1f), icon = Icons.Default.CameraAlt, label = "LOG RUN")
            QuickActionButton(modifier = Modifier.weight(1f), icon = Icons.Default.TrendingUp, label = "PROGRESS")
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionButton(modifier = Modifier.weight(1f), icon = Icons.Default.PlayCircleOutline, label = "WORKOUTS")
            QuickActionButton(modifier = Modifier.weight(1f), icon = Icons.Default.ShoppingBag, label = "GEAR STORE")
        }
    }
}

@Composable
fun QuickActionButton(modifier: Modifier, icon: ImageVector, label: String) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
            .clickable { }
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
                Text("RUNNING ACADEMY", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text("LEARN & GROW", color = TextGray, fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            item { AcademyCard("5 Tips for Your First Half-Marathon", "Master the fundamentals...", Color(0xFFFFB74D)) }
            item { AcademyCard("Choosing the Right Shoes", "Find the perfect fit...", Color(0xFF81C784)) }
            item { AcademyCard("Pre-Run Nutrition", "Fuel your body right...", Color(0xFF64B5F6)) }
        }
    }
}

@Composable
fun AcademyCard(title: String, desc: String, imageColor: Color) {
    Column(
        modifier = Modifier
            .width(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground)
            .clickable { }
    ) {
        // Placeholder for Image
        Box(modifier = Modifier.fillMaxWidth().height(120.dp).background(imageColor)) {
            Icon(
                Icons.Default.BookmarkBorder, contentDescription = null, tint = Color.White,
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).size(24.dp).background(Color.Black.copy(alpha=0.3f), CircleShape).padding(4.dp)
            )
        }
        Column(modifier = Modifier.padding(12.dp)) {
            Text("5 MIN READ", color = YellowAccent, fontSize = 9.sp, modifier = Modifier.border(1.dp, YellowAccent, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(desc, color = TextGray, fontSize = 11.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}