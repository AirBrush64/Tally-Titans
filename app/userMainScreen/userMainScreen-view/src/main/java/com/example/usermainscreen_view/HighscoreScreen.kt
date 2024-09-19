package com.example.usermainscreen_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usermainscreen_data.UserApiInterface
import com.example.usermainscreen_viewmodel.UserViewModel
import com.example.usermainscreen_viewmodel.UserViewModelFactory
import androidx.compose.foundation.isSystemInDarkTheme


// Highscore Screen Anischt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighscoreScreen(
    viewModel: UserViewModel = viewModel(factory = UserViewModelFactory()),
    navController: NavController
) {
    // Beobachten der Highscores vom ViewModel
    val highscores by viewModel.highscores.collectAsState()

    // Laden der Highscores, wenn der Screen angezeigt wird
    LaunchedEffect(Unit) {
        viewModel.loadHighscores()
    }

    // Sortiere die Highscores nach der Punktzahl in absteigender Reihenfolge
    val sortedHighscores = highscores.sortedByDescending { it.highscore }

    // Scaffold für die TopAppBar und den Highscore-Inhalt
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Highscores") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { paddingValues ->
            // Anzeige der Liste der Highscores
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues), // Padding für den Platz unter der TopAppBar
                color = MaterialTheme.colorScheme.background
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(sortedHighscores) { highscore ->
                        HighscoreItem(highscore = highscore)
                    }
                }
            }
        }
    )
}

@Composable
fun HighscoreItem(highscore: UserApiInterface.HighscoreResponse) {
    val isDarkTheme = isSystemInDarkTheme()  // Überprüft, ob der Dark Mode aktiviert ist

    // Farbe der Boxen basierend auf dem Modus
    val boxColor = if (isDarkTheme) Color.White else Color.Black
    val textColor = if (isDarkTheme) Color.Black else Color.White

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = boxColor,  // Box-Farbe basierend auf Dark/Light Mode
                shape = RoundedCornerShape(12.dp)  // Abgerundete Ecken
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = highscore.username,
                style = MaterialTheme.typography.titleMedium,
                color = textColor,  // Textfarbe basierend auf dem Modus
                modifier = Modifier.weight(1f)
            )
            Text(
                text = highscore.highscore.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}



