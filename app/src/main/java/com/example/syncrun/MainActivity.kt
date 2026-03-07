package com.example.syncrun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.syncrun.ui.login.LoginScreen
import com.example.syncrun.ui.theme.SYNCRUNTheme

// Import semua screen yang sudah dibuat
import com.example.syncrun.ui.theme.screen.SplashScreen
import com.example.syncrun.ui.theme.screen.setup.Setup1Screen
import com.example.syncrun.ui.theme.screen.setup.Setup2Screen
import com.example.syncrun.ui.theme.screen.setup.Setup3Screen
import com.example.syncrun.ui.theme.screen.setup.Setup4Screen
import com.example.syncrun.ui.theme.screen.Home.HomeScreen
import com.example.syncrun.ui.theme.screen.Home.GearScreen
import com.example.syncrun.ui.theme.screen.calendar.CalendarScreen
import com.example.syncrun.ui.theme.screen.fuel.FuelScreen
import com.example.syncrun.ui.theme.screen.user.UserScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SYNCRUNTheme {
                SyncRunApp()
            }
        }
    }
}

@Composable
fun SyncRunApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        // 1. SPLASH SCREEN
        composable("splash") {
            SplashScreen(onTimeout = {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }

        // 2. LOGIN SCREEN
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // Arahkan ke Setup 1 setelah login (Prototype Flow)
                    navController.navigate("setup1") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // 3. SETUP 1 (PROFILE & GOALS)
        composable("setup1") {
            Setup1Screen(
                onNextClick = { navController.navigate("setup2") }
            )
        }

        // 4. SETUP 2 (RUNNING BASELINE)
        composable("setup2") {
            Setup2Screen(
                onBackClick = { navController.popBackStack() },
                onNextClick = { navController.navigate("setup3") }
            )
        }

        // 5. SETUP 3 (OCR SCHEDULE)
        composable("setup3") {
            Setup3Screen(
                onBackClick = { navController.popBackStack() },
                onNextClick = { navController.navigate("setup4") }
            )
        }

        // 6. SETUP 4 (PREFERENCES)
        composable("setup4") {
            Setup4Screen(
                onBackClick = { navController.popBackStack() },
                onFinishClick = {
                    // Selesai Setup, masuk ke Home dan hapus semua history setup
                    navController.navigate("home") {
                        popUpTo("setup1") { inclusive = true }
                    }
                }
            )
        }

        // 7. HOME SCREEN
        composable("home") {
            HomeScreen()
        }

        // 8. CALENDAR SCREEN
        composable("calendar") {
            CalendarScreen()
        }

        // 9. FUEL SCREEN
        composable("fuel") {
            FuelScreen()
        }

        // 10. USER / PROFILE SCREEN
        composable("profile") {
            UserScreen(
                onLogoutClick = {
                    // Kembali ke login dan hapus seluruh backstack
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // 11. GEAR STORE SCREEN
        composable("gear") {
            GearScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}