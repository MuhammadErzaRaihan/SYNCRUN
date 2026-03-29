package com.example.syncrun.ui.theme.screen.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.syncrun.ui.theme.component.NavMenu
import com.example.syncrun.ui.theme.component.SyncRunBottomBar

// --- DEFINISI WARNA LOKAL ---
private val DarkBackground = Color(0xFF16171D)
private val CardBackground = Color(0xFF22232A)
private val CyanAccent = Color(0xFF00E5FF)
private val YellowAccent = Color(0xFFC6FF00)
private val TextGray = Color(0xFF9E9E9E)

@Composable
fun GearScreen(
    viewModel: GearViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val currentNavMenu by viewModel.currentNavMenu.collectAsState()
    val products by viewModel.products.collectAsState()
    val uriHandler = LocalUriHandler.current

    val gearOfTheMonth = products.find { it.isGearOfTheMonth }
    val regularProducts = products.filter { !it.isGearOfTheMonth }

    Scaffold(
        bottomBar = {
            SyncRunBottomBar(
                currentRoute = currentNavMenu,
                onItemClick = { viewModel.updateNavMenu(it) },
                onFabClick = {  }
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
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
                                Text("CURATED BY PROFESSIONALS & COMMUNITY", color = TextGray, fontSize = 10.sp, letterSpacing = 0.5.sp)
                            }
                        }
                        
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF2E7D32).copy(alpha = 0.2f))
                                .border(1.dp, Color(0xFF2E7D32), RoundedCornerShape(16.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Verified, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("VERIFIED", color = Color(0xFF4CAF50), fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // GEAR OF THE MONTH
            gearOfTheMonth?.let { gear ->
                item(span = { GridItemSpan(2) }) {
                    GearOfTheMonthSection(gear) { uriHandler.openUri(gear.link) }
                }
                item(span = { GridItemSpan(2) }) {
                    Text(
                        "DISCOVER MORE",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            // GRID PRODUK
            items(regularProducts) { product ->
                ProductCard(product) { uriHandler.openUri(product.link) }
            }

            // FOOTER CARDS
            item(span = { GridItemSpan(2) }) {
                Column(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)) {
                    // Affiliate Disclosure
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(CardBackground)
                            .padding(16.dp)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(24.dp))
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
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF3E232B))
                            .border(1.dp, Color(0xFF5A323E), RoundedCornerShape(12.dp))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.AutoMirrored.Filled.DirectionsRun, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
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

@Composable
fun GearOfTheMonthSection(gear: GearProduct, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image
            Image(
                painter = painterResource(id = gear.imageRes),
                contentDescription = gear.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Overlay Gradient for readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                            startY = 0f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(YellowAccent)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("GEAR OF THE MONTH", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
                
                Column {
                    Text(
                        text = gear.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 24.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = YellowAccent, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(gear.rating, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("(${gear.reviewCount})", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                        }
                        
                        Text(gear.price, color = CyanAccent, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: GearProduct, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = product.category,
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = product.title,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = "Rating", tint = YellowAccent, modifier = Modifier.size(10.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = product.rating, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "| ${product.sold} Sold", color = TextGray, fontSize = 10.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.price,
                color = CyanAccent,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White.copy(alpha = 0.1f))
                    .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "TOKOPEDIA",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.OpenInNew, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                }
            }
        }
    }
}

// --- PREVIEW ---
@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = true)
@Composable
fun GearScreenPreview() {
    GearScreen(viewModel = GearViewModel())
}
