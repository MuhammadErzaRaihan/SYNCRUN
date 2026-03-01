package com.example.syncrun.ui.theme.screen.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Mengambil komponen Bottom Bar dari package yang Anda sebutkan
import com.example.syncrun.ui.theme.component.NavMenu
import com.example.syncrun.ui.theme.component.SyncRunBottomBar

// --- DEFINISI WARNA LOKAL ---
private val DarkBackground = Color(0xFF16171D)
private val CardBackground = Color(0xFF22232A)
private val CyanAccent = Color(0xFF00E5FF)
private val YellowAccent = Color(0xFFC6FF00)
private val TextGray = Color(0xFF9E9E9E)

// --- DATA CLASS UNTUK DUMMY PRODUCT ---
data class GearProduct(
    val category: String,
    val title: String,
    val rating: String,
    val price: String,
    val headerColor: Color,
    val icon: ImageVector
)

@Composable
fun GearScreen(
    onBackClick: () -> Unit = {}
) {
    // State untuk Bottom Navigation Bar
    var currentNavMenu by remember { mutableStateOf(NavMenu.HOME) }

    // Dummy Data Produk (Sebagai Placeholder)
    val products = listOf(
        GearProduct("RUNNING SHOES", "Nike Air Zoom Pegasus 40", "4.8", "Rp 1.899.000", Color(0xFFFF4081), Icons.Default.DirectionsRun),
        GearProduct("RECOVERY TOOLS", "Foam Roller - Muscle Recovery", "4.9", "Rp 185.000", Color(0xFF00B0FF), Icons.Default.TrackChanges),
        GearProduct("APPAREL", "Running Compression Socks", "4.6", "Rp 125.000", Color(0xFFFFEA00), Icons.Default.Checkroom),
        GearProduct("NUTRITION", "Energy Gel Pack (12pcs)", "4.7", "Rp 220.000", Color(0xFFB388FF), Icons.Default.FlashOn),
        GearProduct("TECH", "GPS Running Watch", "4.8", "Rp 2.450.000", Color(0xFF37474F), Icons.Default.Watch),
        GearProduct("SAFETY", "Reflective Safety Vest", "4.5", "Rp 85.000", Color(0xFFFF6D00), Icons.Default.HealthAndSafety)
    )

    Scaffold(
        bottomBar = {
            SyncRunBottomBar(
                currentRoute = currentNavMenu,
                onItemClick = { currentNavMenu = it },
                onFabClick = { /* Aksi Tombol AI Coach */ }
            )
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // HEADER SECTION
            item(span = { GridItemSpan(2) }) {
                Column {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(CardBackground)
                                .clickable { onBackClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("RUNNER'S GEAR", color = YellowAccent, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text("PREMIUM AFFILIATE STORE", color = TextGray, fontSize = 10.sp, letterSpacing = 1.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Info Banner
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF1E2F38))
                            .border(1.dp, Color(0xFF2B4D59), RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Curated products for peak performance. SYNCRUN earns a small commission.",
                            color = CyanAccent,
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // GRID PRODUK
            items(products) { product ->
                ProductCard(product)
            }

            // FOOTER CARDS
            item(span = { GridItemSpan(2) }) {
                Column(modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)) {
                    // Affiliate Disclosure
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(CardBackground)
                            .padding(16.dp)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("AFFILIATE DISCLOSURE", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "SYNCRUN EARNS A SMALL COMMISSION WHEN YOU PURCHASE THROUGH OUR LINKS. THIS HELPS US KEEP THE APP FREE AND IMPROVE YOUR TRAINING EXPERIENCE.",
                                color = TextGray,
                                fontSize = 9.sp,
                                lineHeight = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Shoe Reminder
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF3E232B)) // Warna kemerahan gelap
                            .border(1.dp, Color(0xFF5A323E), RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.DirectionsRun, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("RUNNING SHOE REMINDER", color = Color(0xFFFF8A80), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "REPLACE YOUR SHOES EVERY 400-500 KM TO PREVENT INJURIES. TRACK YOUR MILEAGE IN THE APP!",
                                color = Color.LightGray,
                                fontSize = 9.sp,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- KOMPONEN KARTU PRODUK ---
@Composable
fun ProductCard(product: GearProduct) {
    val buttonGradient = Brush.horizontalGradient(listOf(YellowAccent, CyanAccent))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
    ) {
        // Bagian Atas (Warna Background & Icon)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(product.headerColor)
        ) {
            // Label Kategori
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = product.category,
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            // Icon Produk di tengah
            Icon(
                imageVector = product.icon,
                contentDescription = product.title,
                tint = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
            )
        }

        // Bagian Bawah (Info & Tombol)
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = product.title,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = "Rating", tint = YellowAccent, modifier = Modifier.size(10.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = product.rating, color = TextGray, fontSize = 10.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.price,
                color = CyanAccent,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Tombol "Buy on Shopee"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(buttonGradient)
                    .clickable { /* Aksi buka link */ },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "BUY ON SHOPEE",
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.OpenInNew, contentDescription = null, tint = Color.Black, modifier = Modifier.size(12.dp))
                }
            }
        }
    }
}

// --- PREVIEW ---
@Preview(showSystemUi = true)
@Composable
fun GearScreenPreview() {
    GearScreen()
}