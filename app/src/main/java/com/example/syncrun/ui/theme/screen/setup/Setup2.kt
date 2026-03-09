package com.example.syncrun.ui.theme.screen.setup

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.syncrun.R

@Composable
fun Setup2Screen(
    viewModel: Setup2ViewModel = viewModel(),
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val easyPace by viewModel.easyPace.collectAsState()
    val isCalculated by viewModel.isCalculated.collectAsState()

    val intervalPace by viewModel.intervalPace.collectAsState()
    val longRunPace by viewModel.longRunPace.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        SetupProgressBar(currentStep = 2)

        Column(modifier = Modifier.weight(1f)) {
            SetupHeaderSection(
                step = "",
                title = stringResource(R.string.running_baseline_title),
                subtitle = stringResource(R.string.ai_calibration_subtitle),
                icon = Icons.Default.MonitorHeart,
                iconTint = YellowAccent
            )

            SetupTextField(stringResource(R.string.avg_easy_pace_label), easyPace, "e.g., 00:00", "/km", true) { viewModel.updatePace(it) }
            Spacer(modifier = Modifier.height(24.dp))

            Text(stringResource(R.string.interval_pace_label), color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            PaceResultBox(isCalculated, "${stringResource(R.string.workout_type_interval)}: $intervalPace /km ✨")

            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.long_run_pace_label), color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            PaceResultBox(isCalculated, "${stringResource(R.string.workout_type_long_run)}: $longRunPace /km ✨")
            Spacer(modifier = Modifier.height(32.dp))
            
            if (!isCalculated) {
                Button(
                    onClick = { viewModel.calculateAI() },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAFB42B))
                ) {
                    Text("⚡ " + stringResource(R.string.calculate_ai_paces), color = Color.White, fontWeight = FontWeight.Bold)
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CyanAccent, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        "✨ " + stringResource(R.string.calibration_complete) + "\n" + stringResource(R.string.calibration_complete_desc),
                        color = TextGray,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Bottom Navigation
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.weight(0.3f).height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.DarkGray)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
            }
            Button(
                onClick = onNextClick,
                modifier = Modifier.weight(1f).height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAFB42B)),
                enabled = isCalculated
            ) {
                Text(stringResource(R.string.next_button), color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun PaceResultBox(isCalculated: Boolean, textResult: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .border(1.dp, if(isCalculated) CyanAccent else Color.DarkGray, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        if(isCalculated) Text(textResult, color = CyanAccent, fontSize = 18.sp)
        else Text("--------\n" + stringResource(R.string.waiting_ai_calc), color = Color.Gray, textAlign = TextAlign.Center, fontSize = 12.sp)
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
