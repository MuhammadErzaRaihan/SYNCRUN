package com.example.syncrun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.syncrun.ui.login.LoginScreen
import com.example.syncrun.ui.theme.SYNCRUNTheme

// Import semua screen
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
import com.example.syncrun.ui.theme.screen.user.SettingScreen
import com.example.syncrun.ui.theme.screen.chat.ChatScreen
import com.example.syncrun.ui.theme.component.SyncRunBottomBar
import com.example.syncrun.ui.theme.component.NavMenu

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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Tentukan screen mana saja yang menampilkan BottomBar
    val showBottomBar = when (currentDestination?.route) {
        "home", "calendar", "fuel", "profile" -> true
        else -> false
    }

    // Map route ke NavMenu enum untuk highlight icon
    val currentNavMenu = when (currentDestination?.route) {
        "home" -> NavMenu.HOME
        "calendar" -> NavMenu.CALENDAR
        "fuel" -> NavMenu.FUEL
        "profile" -> NavMenu.PROFILE
        else -> NavMenu.HOME
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                SyncRunBottomBar(
                    currentRoute = currentNavMenu,
                    onItemClick = { menu ->
                        navController.navigate(menu.name.lowercase()) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onFabClick = {
                        navController.navigate("chat")
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController, 
            startDestination = "splash",
            modifier = Modifier.padding(if (showBottomBar) innerPadding else androidx.compose.foundation.layout.PaddingValues(0.dp))
        ) {
            composable("splash") {
                SplashScreen(onTimeout = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                })
            }

            composable("login") {
                LoginScreen(onLoginSuccess = {
                    navController.navigate("setup1") {
                        popUpTo("login") { inclusive = true }
                    }
                })
            }

            composable("setup1") { Setup1Screen(onNextClick = { navController.navigate("setup2") }) }
            composable("setup2") { Setup2Screen(onBackClick = { navController.popBackStack() }, onNextClick = { navController.navigate("setup3") }) }
            composable("setup3") { Setup3Screen(onBackClick = { navController.popBackStack() }, onNextClick = { navController.navigate("setup4") }) }
            composable("setup4") { 
                Setup4Screen(
                    onBackClick = { navController.popBackStack() }, 
                    onFinishClick = {
                        navController.navigate("home") { popUpTo("setup1") { inclusive = true } }
                    }
                ) 
            }

            composable("home") { HomeScreen(onNavigateToRoute = { route -> navController.navigate(route) }) }
            composable("calendar") { CalendarScreen(onNavigateToRoute = { route -> navController.navigate(route) }) }
            composable("fuel") { FuelScreen(onNavigateToRoute = { route -> navController.navigate(route) }) }
            composable("profile") { 
                UserScreen(
                    onNavigateToRoute = { route -> navController.navigate(route) },
                    onLogoutClick = {
                        navController.navigate("login") { popUpTo(0) { inclusive = true } }
                    },
                    onSettingsClick = { navController.navigate("settings") }
                )
            }

            composable("chat") { 
                ChatScreen(onBackClick = { navController.popBackStack() }) 
            }

            composable("gear") { GearScreen(onBackClick = { navController.popBackStack() }) }
            composable("settings") { SettingScreen(onBackClick = { navController.popBackStack() }) }
        }
    }
}
