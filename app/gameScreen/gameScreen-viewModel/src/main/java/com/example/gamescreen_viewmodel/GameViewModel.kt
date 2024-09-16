package com.example.usermainscreen_viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.example.gamescreen_data.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel(private val repository: GameRepository, private val context: Context) : ViewModel() {

    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft: StateFlow<Long> get() = _timeLeft

    private val _currentWord = MutableStateFlow("Loading...")  // Initialisiere mit einem Standardwert
    val currentWord: StateFlow<String> get() = _currentWord

    private val _result = MutableStateFlow<String?>(null)
    val result: StateFlow<String?> get() = _result

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> get() = _isTimerRunning

    private val _tries = MutableStateFlow(3)  // Initialisiere mit einem Standardwert von 3
    val tries: StateFlow<Int> get() = _tries

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> get() = _score

    private val _highscore = MutableStateFlow(0)
    val highscore: StateFlow<Int> get() = _highscore

    private val _userId = MutableStateFlow<Int>(0)  // Default-Wert von 0
    val userId: StateFlow<Int> get() = _userId  // Nur lesbarer Zugang

    init {
        loadUserIdFromPreferences()
    }

    private fun loadUserIdFromPreferences() {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val savedUserId = sharedPreferences.getInt("user_id", -1)
        if (savedUserId != -1) {
            _userId.value = savedUserId
        } else {
            Log.d("GameViewModel", "No user_id found in SharedPreferences")
        }
    }

    fun startGame() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Hole die user_id aus dem MutableStateFlow
                val userId = _userId.value.toString()

                // Abrufen der Spielinformationen (Wort und Countdown)
                val gameInfoResult = repository.getGameInfo(userId)
                gameInfoResult.fold(
                    onSuccess = { response ->
                        _currentWord.value = response.word  // Setzt das aktuelle Wort
                        _timeLeft.value = response.timeLeft.toLong()  // Initialisiert den Countdown
                        _tries.value = 3  // Setzt die Versuche für die neue Runde zurück
                        _result.value = null  // Setzt das Ergebnis zurück
                        _score.value = response.personal_score  // Setzt den persönlichen Score des Benutzers
                        // Starte den Countdown
                        startCountdown(response.timeLeft)
                    },
                    onFailure = {
                        _currentWord.value = "Fehler beim Abrufen des Spiels."
                    }
                )
            } catch (e: Exception) {
                _result.value = "Fehler beim Abrufen der Spielinformationen."
            }
        }
    }

    private fun startCountdown(initialTime: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            var timeLeft = initialTime
            while (timeLeft > 0) {
                _timeLeft.value = timeLeft.toLong()
                delay(1000)  // 1 Sekunde warten
                timeLeft -= 1
            }

            // Wenn der Countdown abgelaufen ist, setze den Zustand
            _timeLeft.value = 0
            _result.value = "Zeit abgelaufen!"
            // Starte die nächste Runde
            startGame()
        }
    }

    fun submitAnswer(guessedCount: Int) {
        viewModelScope.launch {
            val correctCount = _currentWord.value.toSet().size
            if (guessedCount == correctCount) {
                _result.value = "Richtig! Es gibt $correctCount verschiedene Buchstaben."
                calculateAndSubmitPoints(
                    _userId.value.toString(),  // Greife auf den Wert zu und gib "No User ID" als Fallback aus
                    _currentWord.value.length,      // Länge des aktuellen Worts
                    _tries.value,            // Versuche übrig
                    _timeLeft.value.toInt()         // Zeit übrig, hier in Int konvertiert
                )
            } else {
                val remainingAttempts = _tries.value - 1
                _tries.value = remainingAttempts

                if (remainingAttempts == 0) {
                    _result.value = "Keine Versuche mehr! Die richtige Antwort ist $correctCount."
                } else {
                    _result.value = "Falsch! Noch $remainingAttempts Versuche."
                }
            }
        }
    }

    private fun calculateAndSubmitPoints(user_id: String, wordLength: Int, tries: Int, timeLeft: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.calculatePoints(user_id, wordLength, tries, timeLeft)
            result.fold(
                onSuccess = { response ->
                    _score.value = response.points  // Setze den neuen Punktestand
                    _score.value += response.new_highscore  // Setze den neuen Highscore
                    _result.value = "Punkte erfolgreich berechnet!"
                },
                onFailure = {
                    _result.value = "Fehler bei der Punkteberechnung."
                }
            )
        }
    }
}

