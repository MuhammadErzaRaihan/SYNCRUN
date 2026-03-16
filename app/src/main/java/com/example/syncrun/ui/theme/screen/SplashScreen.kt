package com.example.syncrun.ui.theme.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.syncrun.R
import kotlinx.coroutines.delay

// Perubahan pada D:/SYNCRUN/app/src/main/java/com/example/syncrun/ui/theme/screen/SplashScreen.kt

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Memastikan asset dipanggil dengan tepat
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("animasi loading.json"))

    LaunchedEffect(Unit) {
        delay(5000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo_1),
                contentDescription = "Logo Aplikasi",
                modifier = Modifier.size(150.dp) // Ukuran logo disesuaikan
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Menggunakan versi LottieAnimation yang lebih sederhana untuk auto-play
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}