package com.example.syncrun.ui.theme.screen.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.syncrun.R
import com.example.syncrun.ui.theme.SYNCRUNTheme

// --- TEMA WARNA LOKAL ---
private val YellowAccent = Color(0xFFC6FF00)
private val CyanAccent = Color(0xFF00E5FF)
private val TextGray = Color(0xFF9E9E9E)
private val CardBackground = Color(0xFF22232A)

@Composable
fun UserScreen(
    onNavigateToRoute: (String) -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Spacer(modifier = Modifier.height(32.dp)) }

            // --- PROFIL HEADER ---
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, YellowAccent, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background), // Ganti dengan foto user
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("e", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Marathoner • Binusian 26", color = TextGray, fontSize = 14.sp)
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }

            // --- STATS RINGKAS ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProfileStatCard(Modifier.weight(1f), "128", "KM RUN")
                    ProfileStatCard(Modifier.weight(1f), "12", "EVENTS")
                    ProfileStatCard(Modifier.weight(1f), "Gold", "RANK")
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }

            // --- MENU LIST ---
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(CardBackground)
                ) {
                    MenuItem(Icons.Default.Person, "Edit Profile") { }
                    Divider(color = Color.White.copy(alpha = 0.05f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    MenuItem(Icons.Default.Settings, "Settings", onClick = onSettingsClick)
                    Divider(color = Color.White.copy(alpha = 0.05f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    MenuItem(Icons.Default.Shield, "Privacy & Security") { }
                    Divider(color = Color.White.copy(alpha = 0.05f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    MenuItem(Icons.Default.Help, "Help Center") { }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // --- LOGOUT BUTTON ---
            item {
                TextButton(
                    onClick = onLogoutClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout", color = Color.Red.copy(alpha = 0.8f), fontWeight = FontWeight.Bold)
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) } // Extra padding for BottomBar
        }
    }
}

@Composable
fun ProfileStatCard(modifier: Modifier, value: String, label: String) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, color = CyanAccent, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(label, color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MenuItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = YellowAccent, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = Color.White, fontSize = 14.sp, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextGray, modifier = Modifier.size(20.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun UserScreenPreview() {
    SYNCRUNTheme {
        UserScreen()
    }
}
