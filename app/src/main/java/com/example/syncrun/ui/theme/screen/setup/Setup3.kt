package com.example.syncrun.ui.theme.screen.setup

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.School
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.syncrun.R
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun Setup3Screen(
    viewModel: Setup3ViewModel = viewModel(),
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val isUploaded by viewModel.isUploaded.collectAsState()
    val context = LocalContext.current
    val isProcessing by viewModel.isProcessing.collectAsState()
    val showDialog by viewModel.showVerificationDialog.collectAsState()
    val detectedSchedule by viewModel.detectedSchedule.collectAsState()
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.processKrsImages(context, listOf(it)) }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog() },
            containerColor = MaterialTheme.colorScheme.surface,
            title = {
                Text(stringResource(R.string.confirm_schedule_title), color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth().heightIn(max = 350.dp)) {
                    Text(stringResource(R.string.krs_ai_detect_prefix), color = TextGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val groupedByDay = detectedSchedule.entries
                        .groupBy { 
                            val date = LocalDate.of(2026, 2, it.key)
                            date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
                        }
                    
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        groupedByDay.forEach { (dayName, dailyEntries) ->
                            item {
                                Text(dayName, color = CyanAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color.DarkGray)
                            }
                            
                            dailyEntries.forEach { entry ->
                                items(entry.value) { session ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Icon(
                                            Icons.Default.School, 
                                            contentDescription = null, 
                                            tint = YellowAccent, 
                                            modifier = Modifier.size(18.dp).padding(top = 2.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(session.title, color = MaterialTheme.colorScheme.onSurface, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                            Text(session.duration ?: "", color = TextGray, fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.confirmSchedule() },
                    colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
                ) {
                    Text(stringResource(R.string.confirm_button), color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissDialog() }) {
                    Text(stringResource(R.string.cancel_button), color = Color.Gray)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        SetupProgressBar(currentStep = 3)

        Column(modifier = Modifier.weight(1f)) {
            SetupHeaderSection(
                step = "", 
                title = stringResource(R.string.class_schedule_ocr_title), 
                subtitle = stringResource(R.string.ai_vision_subtitle),
                icon = Icons.Default.CalendarMonth, 
                iconTint = YellowAccent
            )

            Spacer(modifier = Modifier.height(32.dp))

            val stroke = Stroke(width = 4f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .drawBehind { 
                        drawRoundRect(
                            color = if (isUploaded) CyanAccent else Color.Gray, 
                            style = stroke, 
                            cornerRadius = CornerRadius(12.dp.toPx())
                        ) 
                    }
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (isProcessing) {
                    CircularProgressIndicator(color = CyanAccent)
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = if (isUploaded) Icons.Default.CheckCircle else Icons.Default.Upload,
                            contentDescription = null,
                            tint = if (isUploaded) CyanAccent else YellowAccent,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            stringResource(if (isUploaded) R.string.krs_upload_success else R.string.upload_krs_screenshot),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            stringResource(if (isUploaded) R.string.krs_detected_desc else R.string.ai_detect_krs_desc),
                            color = TextGray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Bottom Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .weight(0.3f)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.DarkGray)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
            }
            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
            ) {
                Text(stringResource(R.string.next_button), color = Color.Black, fontWeight = FontWeight.Bold)
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
