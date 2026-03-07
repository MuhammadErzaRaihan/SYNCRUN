package com.example.syncrun.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- WARNA & PRESET ---
val DarkBackground = Color(0xFF1A1B22)
val CardBackground = Color(0xFF24252E)
val TextGradientColors = listOf(Color(0xFF81C784), Color(0xFF4DD0E1))
val ButtonGradientColors = listOf(Color(0xFFFFFF00), Color(0xFF00E5FF))

@Composable
fun LoginScreen(
    // Callback ini yang akan dipanggil di MainActivity untuk pindah layar
    onLoginSuccess: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // --- HEADER (LOGO & JUDUL) ---
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = null,
                tint = Color(0xFFFFFF00),
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            GradientText(
                text = "SYNCRUN",
                gradient = Brush.horizontalGradient(TextGradientColors),
                style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            )

            Text(
                text = "SYNCHRONIZE • TRAIN • DOMINATE",
                color = Color.Gray,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // --- FEATURE CARDS (PLACEHOLDER) ---
            // Tombol ini hanya visual (null action)
            FeatureCard(Icons.Default.MonitorHeart, "AI-powered training", Color(0xFFFF5252))
            Spacer(modifier = Modifier.height(16.dp))
            FeatureCard(Icons.Default.CalendarToday, "Smart scheduling", Color(0xFF40C4FF))
            Spacer(modifier = Modifier.height(16.dp))
            FeatureCard(Icons.Default.Savings, "Budget-optimized nutrition", Color(0xFFFFD740))

            Spacer(modifier = Modifier.weight(1f))

            // --- TOMBOL UTAMA (BYPASS) ---
            GradientButton(
                text = "CONTINUE WITH GMAIL",
                icon = Icons.Default.Email,
                onClick = {
                    // DI SINI LOGIKA BYPASS-NYA
                    // Tidak perlu cek email/password, langsung panggil onLoginSuccess
                    onLoginSuccess()
                }
            )

            // Opsional: Tombol bypass rahasia kecil di bawah untuk testing cepat
            TextButton(onClick = { onLoginSuccess() }) {
                Text("Skip Login (Prototype Mode)", color = Color.Gray, fontSize = 10.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// --- KOMPONEN UI ---

@Composable
fun GradientText(text: String, gradient: Brush, style: TextStyle) {
    Text(text = text, style = style.copy(brush = gradient))
}

@Composable
fun FeatureCard(icon: ImageVector, text: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
            .background(CardBackground.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun GradientButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.horizontalGradient(ButtonGradientColors))
            .clickable { onClick() }, // Klik langsung trigger aksi
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}