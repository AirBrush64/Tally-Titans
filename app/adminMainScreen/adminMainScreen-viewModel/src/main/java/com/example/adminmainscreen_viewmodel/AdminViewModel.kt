package com.example.usermainscreen_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usermainscreen_data.AdminApiInterface
import com.example.usermainscreen_data.AdminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel(private val repository: AdminRepository) : ViewModel() {

    //Liste von Highscore aus der Response von der API
    private val _highscores = MutableStateFlow<List<AdminApiInterface.HighscoreResponse>>(emptyList())
    val highscores: StateFlow<List<AdminApiInterface.HighscoreResponse>> get() = _highscores

    // Funktion zum Laden der Highscore
    fun loadHighscores() {
        viewModelScope.launch {
            val result = repository.getHighscores()
            result.onSuccess {
                _highscores.value = it
            }.onFailure {
                // Fehlerbehandlung wenn die Werte von den Highscores nicht gesetzt werden kann
                _highscores.value = listOf(AdminApiInterface.HighscoreResponse("Error", 0))
            }
        }
    }
}
