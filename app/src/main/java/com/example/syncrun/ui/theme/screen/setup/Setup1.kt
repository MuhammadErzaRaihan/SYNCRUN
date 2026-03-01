package com.example.syncrun.ui.theme.screen.setup


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setup1Screen(
    viewModel: Setup1ViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNextClick: () -> Unit
) {
    val name by viewModel.name.collectAsState()
    val currentWeight by viewModel.currentWeight.collectAsState()
    val targetWeight by viewModel.targetWeight.collectAsState()
    val selectedGoal by viewModel.selectedGoal.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    val goals = listOf("5K Race", "10K Race", "Half-Marathon (21K)", "Full-Marathon (42K)", "Triathlon", "General Long-Run")

    Column(
        modifier = Modifier.fillMaxSize().background(DarkBackground).padding(24.dp)
    ) {
        SetupProgressBar(currentStep = 1)

        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            SetupHeaderSection("1/4", "Profile & Goals", "SET YOUR AMBITIOUS TARGETS", Icons.Default.MonitorHeart, CyanAccent)

            SetupTextField("NAME", name, "e.g., Your Name") { viewModel.updateName(it) }
            Spacer(modifier = Modifier.height(16.dp))
            SetupTextField("CURRENT WEIGHT (KG)", currentWeight, "e.g., 77") { viewModel.updateCurrentWeight(it) }
            Spacer(modifier = Modifier.height(16.dp))
            SetupTextField("TARGET WEIGHT (KG)", targetWeight, "e.g., 70") { viewModel.updateTargetWeight(it) }
            Spacer(modifier = Modifier.height(16.dp))

            // Goal Dropdown
            Text("GOAL", color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = selectedGoal, onValueChange = {}, readOnly = true,
                    placeholder = { Text("What is your main target?", color = Color.Gray) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = CyanAccent, unfocusedBorderColor = Color.DarkGray, containerColor = FieldBackground, focusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.background(FieldBackground)) {
                    goals.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, color = Color.White) },
                            onClick = { viewModel.updateGoal(selectionOption); expanded = false }
                        )
                    }
                }
            }
        }

        Button(
            onClick = onNextClick,
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(top = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAFB42B))
        ) {
            Text("NEXT", color = DarkBackground, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = true)
@Composable
fun Setup1Preview () {
    Setup1Screen(
        viewModel =Setup1ViewModel(),
        onNextClick = {}
    )
}
