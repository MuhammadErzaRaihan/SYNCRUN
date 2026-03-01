package com.example.syncrun.ui.theme.screen.setup

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun Setup2Screen(
    viewModel: Setup2ViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val easyPace by viewModel.easyPace.collectAsState()
    val isCalculated by viewModel.isCalculated.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(DarkBackground).padding(24.dp)) {
        SetupProgressBar(currentStep = 2)

        Column(modifier = Modifier.weight(1f)) {
            SetupHeaderSection("", "Running Baseline", "AI FOUNDATION CALIBRATION", Icons.Default.MonitorHeart, YellowAccent)

            SetupTextField("AVERAGE EASY PACE", easyPace, "e.g., 00:00", "/km", true) { viewModel.updatePace(it) }
            Spacer(modifier = Modifier.height(24.dp))

            // AI Paces Result Boxes
            Text("INTERVAL PACE", color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            PaceResultBox(isCalculated, "Interval: 06:00 /km ✨")

            Spacer(modifier = Modifier.height(16.dp))
            Text("LONG RUN PACE", color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            PaceResultBox(isCalculated, "Long Run: 07:30 /km ✨")
            Spacer(modifier = Modifier.height(32.dp))

            if (!isCalculated) {
                Button(
                    onClick = { viewModel.calculateAI() },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAFB42B))
                ) {
                    Text("⚡ CALCULATE AI PACES", color = DarkBackground, fontWeight = FontWeight.Bold)
                }
            } else {
                Box(modifier = Modifier.fillMaxWidth().border(1.dp, CyanAccent, RoundedCornerShape(8.dp)).padding(16.dp)) {
                    Text("✨ AI CALIBRATION COMPLETE\nYour paces have been calculated.", color = TextGray, fontSize = 12.sp)
                }
            }
        }

        // Bottom Navigation
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedButton(onClick = onBackClick, modifier = Modifier.weight(0.3f).height(56.dp), shape = RoundedCornerShape(12.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Color.DarkGray)) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Button(
                onClick = onNextClick,
                modifier = Modifier.weight(1f).height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAFB42B)),
                enabled = isCalculated // Hanya bisa Next kalau sudah di calculate
            ) {
                Text("NEXT", color = DarkBackground, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun PaceResultBox(isCalculated: Boolean, textResult: String) {
    Box(modifier = Modifier.fillMaxWidth().height(64.dp).background(FieldBackground, RoundedCornerShape(12.dp)).border(1.dp, if(isCalculated) CyanAccent else Color.DarkGray, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
        if(isCalculated) Text(textResult, color = CyanAccent, fontSize = 18.sp)
        else Text("--------\nWaiting for AI calculation...", color = Color.Gray, textAlign = TextAlign.Center, fontSize = 12.sp)
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = true)
@Composable
fun Setup2Preview() {

    Setup2Screen(
        viewModel = Setup2ViewModel(),
        onBackClick = {},
        onNextClick = {}
    )
}