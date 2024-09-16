package com.example.tallytitans

import GameScreen
import UserMainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login.LoginScreen
import com.example.register_view.RegisterScreen
import com.example.tallytitans.ui.theme.TallyTitansTheme
import com.example.usermainscreen_view.HighscoreScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TallyTitansTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("home") {
            UserMainScreen(navController = navController, onProfileClick = { navController.navigate("home") })
        }
        composable("highscores") {
            HighscoreScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }
        composable("game") {
            GameScreen(navController = navController)
        }
    }
}

