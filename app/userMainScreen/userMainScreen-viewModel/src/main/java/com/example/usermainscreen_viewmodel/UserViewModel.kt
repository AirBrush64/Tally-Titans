package com.example.usermainscreen_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usermainscreen_data.UserApiInterface
import com.example.usermainscreen_data.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _highscores = MutableStateFlow<List<UserApiInterface.HighscoreResponse>>(emptyList())
    val highscores: StateFlow<List<UserApiInterface.HighscoreResponse>> get() = _highscores

    fun loadHighscores() {
        viewModelScope.launch {
            val result = repository.getHighscores()
            result.onSuccess {
                _highscores.value = it
            }.onFailure {
                // Fehlerbehandlung
                _highscores.value = listOf(UserApiInterface.HighscoreResponse("Error", 0))
            }
        }
    }
}