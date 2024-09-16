import android.os.CountDownTimer
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
import com.example.usermainscreen_viewmodel.GameViewModel
import com.example.usermainscreen_viewmodel.GameViewModelFactory
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(LocalContext.current)),
    navController: NavController
) {
    // Beobachten der ViewModel-Daten
    val timeLeft by gameViewModel.timeLeft.collectAsState(initial = 0L)
    val word by gameViewModel.currentWord.collectAsState(initial = "Loading...")
    val attemptsLeft by gameViewModel.tries.collectAsState(initial = 3)
    val highscore by gameViewModel.highscore.collectAsState(initial = 0)
    val result by gameViewModel.result.collectAsState()

    var guessedCount by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Startet das Spiel (Polling für Wort und Countdown läuft im ViewModel)
    LaunchedEffect(Unit) {
        gameViewModel.startGame()
    }

    // Hintergrund- und Textfarben basierend auf dem aktuellen Theme
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground

    // UI für das Spiel
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
            textStyle = LocalTextStyle.current.copy(color = textColor)
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Button zum Einreichen der Antwort
        Button(
            onClick = {
                scope.launch {
                    gameViewModel.submitAnswer(guessedCount.toIntOrNull() ?: 0)
                }
            },
            modifier = Modifier.fillMaxWidth()
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

        // Punktestand
        Text(
            text = "Punkte: $highscore",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

