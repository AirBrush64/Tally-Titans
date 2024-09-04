package com.example.tallytitans

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.login.LoginScreen
import com.example.tallytitans.ui.theme.TallyTitansTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TallyTitansTheme {
                LoginScreen()
            }
        }
    }
}

