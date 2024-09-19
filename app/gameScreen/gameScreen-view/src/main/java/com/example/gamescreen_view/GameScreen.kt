import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gamescreen_viewmodel.GameViewModel
import com.example.gamescreen_viewmodel.GameViewModelFactory
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext

// Game Screen für das Spiel
@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(LocalContext.current)),
    navController: NavController
) {
    // Beobachten der ViewModel-Daten für Veränderungen
    val timeLeft by gameViewModel.timeLeft.collectAsState(initial = 0L)
    val word by gameViewModel.currentWord.collectAsState(initial = "Loading...")
    val attemptsLeft by gameViewModel.tries.collectAsState(initial = 3)
    val highscore by gameViewModel.highscore.collectAsState(initial = 0)
    val result by gameViewModel.result.collectAsState()

    var guessedCount by remember { mutableStateOf("") }
    var isInputEnabled by remember { mutableStateOf(true) }  // Status der Eingabefähigkeit
    val scope = rememberCoroutineScope()

    // Spiel starten, wenn der Screen angezeigt wird
    LaunchedEffect(Unit) {
        gameViewModel.startGame()
    }

    // Beobachte das Wort und aktiviere das Eingabefeld bei einem neuen Wort
    LaunchedEffect(word) {
        isInputEnabled = true  // Setze das Eingabefeld auf aktiv, wenn sich das Wort ändert
        guessedCount = ""  // Leere das Eingabefeld bei einem neuen Wort
    }

    // Hintergrund- und Textfarben basierend auf dem aktuellen Theme
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground

    // Spalte mit allen Views in richtiger Reihnfolge
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Countdown Timer
        Text(
            text = "Time Left: $timeLeft seconds",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Aktuelles Wort
        Text(
            text = word,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Anzeige der verbleibenden Versuche
        Text(
            text = "Versuche übrig: $attemptsLeft",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Eingabefeld für die Anzahl der Buchstaben
        OutlinedTextField(
            value = guessedCount,
            onValueChange = { guessedCount = it },
            label = { Text("Verschiedene Buchstaben", color = textColor) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = textColor),
            enabled = isInputEnabled  // Steuerung der Bearbeitbarkeit
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Button zum Einreichen der Antwort
        Button(
            onClick = {
                scope.launch {
                    val result = guessedCount.toIntOrNull() ?: 0
                    gameViewModel.submitAnswer(result)

                    // Sperre das Textfeld und den Button, wenn attemptsLeft <= 1 ist oder das Ergebnis korrekt ist
                    if (attemptsLeft <= 1 || result == gameViewModel.correctCount.value) {
                        isInputEnabled = false  // Eingabe deaktivieren
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isInputEnabled  // Button auch deaktivieren, wenn keine Eingabe möglich
        ) {
            Text("Antwort eingeben")
        }

        // Ergebnis (Richtig oder Falsch)
        result?.let {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = it,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (it.contains("Richtig", ignoreCase = true)) Color.Green else Color.Red
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Punktestand Anzeige
        Text(
            text = "Punkte: $highscore",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

