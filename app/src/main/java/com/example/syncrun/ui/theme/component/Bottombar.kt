package com.example.syncrun.ui.theme.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- DEFINISI WARNA ---
val BottomBarBackground = Color(0xFF1E1F25) // Warna latar bar agak gelap
val YellowAccent = Color(0xFFC6FF00)
val CyanAccent = Color(0xFF00E5FF)
val TextGray = Color(0xFF757575)
val FabGradient = Brush.linearGradient(listOf(YellowAccent, CyanAccent))

// --- DATA CLASS UNTUK ITEM NAVIGASI ---
enum class NavMenu(val title: String, val icon: ImageVector) {
    HOME("HOME", Icons.Outlined.Home),
    CALENDAR("CALENDAR", Icons.Outlined.CalendarToday),
    FUEL("FUEL", Icons.Outlined.Restaurant), // Menggunakan icon Restaurant sebagai ganti pisau/garpu
    PROFILE("PROFILE", Icons.Outlined.Person)
}

@Composable
fun SyncRunBottomBar(
    currentRoute: NavMenu,
    onItemClick: (NavMenu) -> Unit,
    onFabClick: () -> Unit
) {
    // Box utama untuk menampung BottomBar dan Floating Button (FAB)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp) // Lebih tinggi dari bar biasa agar FAB bisa menonjol ke atas
            .background(Color.Transparent)
    ) {
        // 1. Background Bar (Row)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
                .background(BottomBarBackground),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Kiri
            BottomNavItem(
                item = NavMenu.HOME,
                isSelected = currentRoute == NavMenu.HOME,
                onClick = { onItemClick(NavMenu.HOME) }
            )
            BottomNavItem(
                item = NavMenu.CALENDAR,
                isSelected = currentRoute == NavMenu.CALENDAR,
                onClick = { onItemClick(NavMenu.CALENDAR) }
            )

            // Spacer untuk memberi ruang di tengah bagi FAB
            Spacer(modifier = Modifier.width(64.dp))

            // Kanan
            BottomNavItem(
                item = NavMenu.FUEL,
                isSelected = currentRoute == NavMenu.FUEL,
                onClick = { onItemClick(NavMenu.FUEL) }
            )
            BottomNavItem(
                item = NavMenu.PROFILE,
                isSelected = currentRoute == NavMenu.PROFILE,
                onClick = { onItemClick(NavMenu.PROFILE) }
            )
        }

        // 2. Center Floating Action Button (FAB)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter) // Posisikan di tengah atas Box utama
                .offset(y = 8.dp) // Sesuaikan posisi naiknya
                .size(64.dp)
                // Menambahkan bayangan (glow effect) dengan warna kuning/cyan
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    spotColor = YellowAccent,
                    ambientColor = CyanAccent
                )
                .clip(CircleShape)
                .background(FabGradient)
                .clickable { onFabClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome, // Icon Sparkle
                contentDescription = "AI Generate",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

// --- KOMPONEN ITEM NAVIGASI KECIL ---
@Composable
fun BottomNavItem(
    item: NavMenu,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null, // Menghilangkan efek ripple kotak agar lebih rapi
                onClick = onClick
            )
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = if (isSelected) YellowAccent else TextGray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.title,
            color = if (isSelected) YellowAccent else TextGray,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            letterSpacing = 0.5.sp
        )
    }
}

// --- PREVIEW ---
@Preview(showBackground = true, backgroundColor = 0xFF16171D)
@Composable
fun SyncRunBottomBarPreview() {
    // Menyimpan state lokal hanya untuk keperluan preview agar bisa diklik
    var selectedItem by remember { mutableStateOf(NavMenu.HOME) }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF16171D))) {
        // Letakkan Bottom Bar di bagian bawah layar
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            SyncRunBottomBar(
                currentRoute = selectedItem,
                onItemClick = { selectedItem = it },
                onFabClick = { /* Do something like open AI bottom sheet */ }
            )
        }
    }
}