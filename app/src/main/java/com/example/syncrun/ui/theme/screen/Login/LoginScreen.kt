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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.syncrun.R
import com.example.syncrun.ui.theme.SYNCRUNTheme

// --- WARNA & PRESET ---
val TextGradientColors = listOf(Color(0xFF81C784), Color(0xFF4DD0E1))
val ButtonGradientColors = listOf(Color(0xFFFFFF00), Color(0xFF00E5FF))
private val CyanAccent = Color(0xFF00E5FF)

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                text = stringResource(R.string.slogan),
                color = Color.Gray,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

/*            // --- FEATURE CARDS (PINDAH KE ATAS) ---
            Text(
                text = "Feature",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start).padding(bottom = 12.dp)
            )
            FeatureCard(Icons.Default.MonitorHeart, stringResource(R.string.ai_powered_training), Color(0xFFFF5252))
            Spacer(modifier = Modifier.height(12.dp))
            FeatureCard(Icons.Default.CalendarToday, stringResource(R.string.smart_scheduling), Color(0xFF40C4FF))*/

            Spacer(modifier = Modifier.height(32.dp))

            // --- INPUT FIELDS ---
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = CyanAccent) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = CyanAccent,
                    unfocusedBorderColor = Color.DarkGray
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = CyanAccent) },
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null, tint = Color.Gray)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = CyanAccent,
                    unfocusedBorderColor = Color.DarkGray
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- TOMBOL LOGIN ---
            GradientButton(
                text = "Login",
                icon = Icons.Default.Bolt,
                onClick = {
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        onLoginSuccess()
                    }
                }
            )

            // --- TOMBOL BYPASS (LOGIN INSTAN) ---
            TextButton(
                onClick = onLoginSuccess,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Bypass Login (Developer Mode)", color = CyanAccent.copy(alpha = 0.7f), fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                stringResource(R.string.not_have_account),
                color = Color.White,
                fontSize = 14.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = { onLoginSuccess() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BoxShadowBorder(Color.DarkGray)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Email, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.continue_with_gmail), color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun BoxShadowBorder(color: Color) = androidx.compose.foundation.BorderStroke(1.dp, color)

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
            .height(60.dp)
            .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, color = MaterialTheme.colorScheme.onSurface, fontSize = 13.sp)
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
            .clickable { onClick() },
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
    SYNCRUNTheme {
        LoginScreen()
    }
}
