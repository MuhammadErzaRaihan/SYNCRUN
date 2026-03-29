package com.example.syncrun.ui.theme.screen.Home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.syncrun.R
import com.example.syncrun.ui.theme.component.NavMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GearProduct(
    val category: String,
    val title: String,
    val rating: String,
    val reviewCount: String,
    val sold: String,
    val price: String,
    val headerColor: Color,
    val imageRes: Int,
    val link: String,
    val icon: ImageVector,
    val isGearOfTheMonth: Boolean = false
)

class GearViewModel : ViewModel() {
    private val _currentNavMenu = MutableStateFlow(NavMenu.HOME)
    val currentNavMenu = _currentNavMenu.asStateFlow()

    private val _products = MutableStateFlow(
        listOf(
            GearProduct(
                "RUNNING SHOES",
                "Nike Air Zoom Pegasus Black Bright Crimson Red Sneakers",
                "5.0",
                "3",
                "6",
                "Rp1.997.201",
                Color(0xFF212121),
                R.drawable.gear1,
                "https://www.tokopedia.com/pandasneakersofficial/sepatu-nike-air-zoom-pegasus-black-bright-crimson-red-sneakers-authentic-1732296614467110471",
                Icons.Default.DirectionsRun,
                isGearOfTheMonth = true
            ),
            GearProduct(
                "RECOVERY",
                "Rumble Foam Roller",
                "4.9",
                "1.952",
                "5 rb+",
                "Rp68.888",
                Color(0xFF00B0FF),
                R.drawable.gear2,
                "https://www.tokopedia.com/lbagstore/rumble-foam-roller-alat-bantu-senam-bantal-yoga-pilates-pijat-926-biru",
                Icons.Default.SelfImprovement
            ),
            GearProduct(
                "APPAREL",
                "One Step Ahead OSA High Compression Running Socks",
                "5.0",
                "1.568",
                "4 rb+",
                "Rp160.000",
                Color(0xFFFFEA00),
                R.drawable.gear3,
                "https://www.tokopedia.com/osasports/one-step-ahead-osa-high-compression-running-socks-kaos-kaki-lari-sepeda-cushion-sport-1730859957228700823",
                Icons.Default.Checkroom
            ),
            GearProduct(
                "NUTRITION",
                "EJ Sport Pack (5 Tube) - Energy Gel",
                "4.9",
                "16",
                "100+",
                "Rp45.000",
                Color(0xFFB388FF),
                R.drawable.gear4,
                "https://www.tokopedia.com/comum-bike--padel-jakarta/ej-sport-pack-5-tube-energy-gel-banana-d53fe",
                Icons.Default.Restaurant
            ),
            GearProduct(
                "SUPPLEMENT",
                "Optimum Nutrition Gold Standard 100% Whey Protein 2 lbs",
                "5.0",
                "640",
                "3 rb+",
                "Rp679.000",
                Color(0xFF4CAF50),
                R.drawable.gear5,
                "https://www.tokopedia.com/optimumnutrition/optimum-nutrition-gold-standard-100-whey-protein-2-lbs-primary-source-isolate-susu-protein-powder-suplemen-gym-muscle-protein-1729679707452442750-1734843077763433598",
                Icons.Default.FitnessCenter
            ),
            GearProduct(
                "TECH",
                "Garmin Forerunner 55 FR 55 GPS Smartwatch",
                "5.0",
                "248",
                "500+",
                "Rp1.699.000",
                Color(0xFF37474F),
                R.drawable.gear6,
                "https://www.tokopedia.com/dorangadget/garmin-forerunner-55-fr-55-gps-smartwatch-original-garansi-tam-2th-1731163581796943709",
                Icons.Default.Watch
            ),
            GearProduct(
                "APPAREL",
                "Satisfy MothTech Running Equipment Aged Black Tee",
                "0",
                "0",
                "0",
                "Rp2.999.000",
                Color(0xFF212121),
                R.drawable.gear7,
                "https://www.tokopedia.com/senikersku/satisfy-mothtech-running-equipment-aged-black-tee-100-authentic-1733307568974038175",
                Icons.Default.Checkroom
            ),
            GearProduct(
                "ACCESSORIES",
                "Active Stride Running Cap",
                "4.9",
                "1.185",
                "4 rb+",
                "Rp135.000",
                Color(0xFF03A9F4),
                R.drawable.gear8,
                "https://www.tokopedia.com/duraking/duraking-topi-lari-active-stride-running-cap-blue-1eae1",
                Icons.Default.ShoppingBag
            ),
            GearProduct(
                "RUNNING SHOES",
                "Adidas Adizero Evo SL White Black",
                "5.0",
                "90",
                "250+",
                "Rp2.499.000",
                Color(0xFFE0E0E0),
                R.drawable.gear9,
                "https://www.tokopedia.com/senikersku/adidas-adizero-evo-sl-white-black-100-authentic-1731260000792052895",
                Icons.Default.DirectionsRun
            ),
            GearProduct(
                "TECH",
                "JETE OpenTune Open Ear Sport",
                "4.9",
                "1.114",
                "4 rb+",
                "Rp237.553",
                Color(0xFFFF9800),
                R.drawable.gear10,
                "https://www.tokopedia.com/gojeteindonesia/jete-opentune-open-ear-sport-headset-olahraga-lari-earphone-bluetooth-air-conduction-water-proof-garansi-2-tahun-1730998026922984702",
                Icons.Default.Headphones
            )
        )
    )
    val products = _products.asStateFlow()

    fun updateNavMenu(menu: NavMenu) {
        _currentNavMenu.value = menu
    }
}
