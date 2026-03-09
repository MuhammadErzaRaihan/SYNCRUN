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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.syncrun.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setup1Screen(
    viewModel: Setup1ViewModel = viewModel(),
    onNextClick: () -> Unit
) {
    val name by viewModel.name.collectAsState()
    val currentWeight by viewModel.currentWeight.collectAsState()
    val targetWeight by viewModel.targetWeight.collectAsState()
    val selectedGoal by viewModel.selectedGoal.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    val goals = listOf("5K Race", "10K Race", "Half-Marathon (21K)", "Full-Marathon (42K)", "Triathlon", "General Long-Run")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        SetupProgressBar(currentStep = 1)

        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            SetupHeaderSection(
                step = "1/4",
                title = stringResource(R.string.profile_goals_title),
                subtitle = stringResource(R.string.set_targets_subtitle),
                icon = Icons.Default.MonitorHeart,
                iconTint = CyanAccent
            )

            SetupTextField(stringResource(R.string.name_label), name, "e.g., Your Name") { viewModel.updateName(it) }
            Spacer(modifier = Modifier.height(16.dp))
            SetupTextField(stringResource(R.string.current_weight_label), currentWeight, "e.g., 77") { viewModel.updateCurrentWeight(it) }
            Spacer(modifier = Modifier.height(16.dp))
            SetupTextField(stringResource(R.string.target_weight_label), targetWeight, "e.g., 70") { viewModel.updateTargetWeight(it) }
            Spacer(modifier = Modifier.height(16.dp))

            // Goal Dropdown
            Text(stringResource(R.string.goal_label), color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = selectedGoal, onValueChange = {}, readOnly = true,
                    placeholder = { Text(stringResource(R.string.goal_placeholder), color = Color.Gray) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = CyanAccent,
                        unfocusedBorderColor = Color.DarkGray,
                        containerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    goals.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, color = MaterialTheme.colorScheme.onSurface) },
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
            Text(stringResource(R.string.next_button), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = true)
@Composable
fun Setup1Preview() {
    Setup1Screen(
        viewModel = Setup1ViewModel(),
        onNextClick = {}
    )
}
