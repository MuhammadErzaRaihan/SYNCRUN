package com.example.syncrun.ui.theme.screen.setup

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// --- SCREEN ---
@Composable
fun Setup3Screen(
    viewModel: Setup3ViewModel = viewModel(),
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val isUploaded by viewModel.isUploaded.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(DarkBackground).padding(24.dp)) {
        SetupProgressBar(currentStep = 3)

        Column(modifier = Modifier.weight(1f)) {
            SetupHeaderSection(
                step = "", title = "Class Schedule OCR", subtitle = "AI VISION TECHNOLOGY",
                icon = Icons.Default.CalendarMonth, iconTint = YellowAccent
            )

            // Dashed Border Box for Upload
            val stroke = Stroke(width = 4f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .drawBehind { drawRoundRect(color = if (isUploaded) CyanAccent else Color.DarkGray, style = stroke, cornerRadius = CornerRadius(12.dp.toPx())) }
                    .clickable { viewModel.simulateUpload() },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Upload, contentDescription = null, tint = if (isUploaded) CyanAccent else YellowAccent, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(if (isUploaded) "UPLOAD SUCCESSFUL" else "UPLOAD KRS SCREENSHOT", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(if (isUploaded) "AI has detected your class hours" else "AI will automatically detect your class hours", color = Color.Gray, fontSize = 12.sp)
                }
            }
        }

        // Bottom Navigation
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedButton(onClick = onBackClick, modifier = Modifier.weight(0.3f).height(56.dp), shape = RoundedCornerShape(12.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Color.DarkGray)) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Button(
                onClick = onNextClick, modifier = Modifier.weight(1f).height(56.dp), shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent) // Tombol Cyan karena ini OCR
            ) {
                Text("NEXT", color = DarkBackground, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = true)
@Composable
fun Setup3Preview() {

    Setup3Screen(
        viewModel = Setup3ViewModel(),
        onBackClick = {},
        onNextClick = {}
    )
}