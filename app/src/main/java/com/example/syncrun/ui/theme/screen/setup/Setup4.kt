package com.example.syncrun.ui.theme.screen.setup


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// --- SCREEN ---
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Setup4Screen(
    viewModel: Setup4ViewModel = viewModel(),
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    val wakeUpTime by viewModel.wakeUpTime.collectAsState()
    val budget by viewModel.budget.collectAsState()
    val otherConditions by viewModel.otherConditions.collectAsState()
    val selectedMedical by viewModel.selectedMedical.collectAsState()

    val medicalOptions = listOf("NONE", "KNEE INJURY", "GERD", "ASTHMA")

    Column(modifier = Modifier.fillMaxSize().background(DarkBackground).padding(24.dp)) {
        SetupProgressBar(currentStep = 4)

        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            SetupHeaderSection(
                step = "4/4", title = "Daily Preferences", subtitle = "PERSONALIZE YOUR TRAINING",
                icon = Icons.Default.Schedule, iconTint = CyanAccent
            )

            // Custom TextField dari SetupComponents.kt
            SetupTextField("WAKE-UP TIME", wakeUpTime, "--:-- --") { viewModel.updateWakeUpTime(it) }
            Spacer(modifier = Modifier.height(16.dp))
            SetupTextField("DAILY FOOD BUDGET", budget, "Rp 0") { viewModel.updateBudget(it) }
            Spacer(modifier = Modifier.height(24.dp))

            // Medical History Chips
            Text("Medical History", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                medicalOptions.forEach { option ->
                    val isSelected = selectedMedical.contains(option)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(if (isSelected) FieldBackground else Color.Transparent)
                            .border(1.dp, if (isSelected) Color.Gray else Color.DarkGray, RoundedCornerShape(24.dp))
                            .clickable { viewModel.toggleMedicalCondition(option) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(option, color = if (isSelected) Color.White else Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("OTHER CONDITIONS", color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = otherConditions,
                onValueChange = { viewModel.updateOtherConditions(it) },
                placeholder = { Text("Type your medical history here...", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = CyanAccent, unfocusedBorderColor = Color.DarkGray, containerColor = FieldBackground, focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Bottom Navigation
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedButton(onClick = onBackClick, modifier = Modifier.weight(0.3f).height(56.dp), shape = RoundedCornerShape(12.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Color.DarkGray)) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Button(
                onClick = onFinishClick, modifier = Modifier.weight(1f).height(56.dp), shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent) // Finish button menggunakan Cyan Accent
            ) {
                Text("FINISH SETUP", color = DarkBackground, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = true)
@Composable
fun Setup4Preview() {
    Setup4Screen(
        viewModel = Setup4ViewModel(), // Memberikan instance manual
        onBackClick = {},
        onFinishClick = {}
    )
}