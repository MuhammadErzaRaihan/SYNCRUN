package com.example.syncrun.ui.theme.screen.setup


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- TEMA & WARNA ---
val DarkBackground = Color(0xFF16171D)
val FieldBackground = Color(0xFF22232A)
val CyanAccent = Color(0xFF00E5FF)
val YellowAccent = Color(0xFFC6FF00)
val TextGray = Color(0xFF9E9E9E)
val ProgressGradient = Brush.horizontalGradient(listOf(YellowAccent, CyanAccent))

@Composable
fun SetupProgressBar(currentStep: Int, totalSteps: Int = 4) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("SETUP PROGRESS", color = TextGray, fontSize = 12.sp, letterSpacing = 1.sp)
            Text("$currentStep/$totalSteps", color = YellowAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(4.dp).background(FieldBackground, RoundedCornerShape(2.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(currentStep.toFloat() / totalSteps)
                    .height(4.dp)
                    .background(ProgressGradient, RoundedCornerShape(2.dp))
            )
        }
    }
}

@Composable
fun SetupHeaderSection(step: String, title: String, subtitle: String, icon: ImageVector, iconTint: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)) {
        if(step.isNotEmpty()) {
            Text(step, color = CyanAccent, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
        }
        Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(title, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(subtitle, color = TextGray, fontSize = 10.sp, letterSpacing = 1.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupTextField(label: String, value: String, placeholder: String, suffix: String = "", centerText: Boolean = false, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray, modifier = if(centerText) Modifier.fillMaxWidth() else Modifier, textAlign = if(centerText) TextAlign.Center else TextAlign.Start) },
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, textAlign = if(centerText) TextAlign.Center else TextAlign.Start, fontSize = if(centerText) 24.sp else 16.sp),
            trailingIcon = {
                if (suffix.isNotEmpty()) Text(suffix, color = Color.Gray, modifier = Modifier.padding(end = 16.dp))
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = CyanAccent,
                unfocusedBorderColor = Color.DarkGray,
                containerColor = FieldBackground,
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}