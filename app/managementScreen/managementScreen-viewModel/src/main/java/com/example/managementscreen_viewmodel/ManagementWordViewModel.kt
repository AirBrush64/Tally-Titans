package com.example.managementscreen_viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managementscreen_data.ManagementApiInterface
import com.example.managementscreen_data.ManagementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ManagementWordViewModel(private val repository: ManagementRepository) : ViewModel() {

    // MutableStateFlow für die Liste der Benutzer
    private val _words = MutableStateFlow<List<ManagementApiInterface.WordsResponse>>(emptyList())
    val words: StateFlow<List<ManagementApiInterface.WordsResponse>> = _words.asStateFlow()


    // Benutzerliste laden
    fun loadWords() {
        viewModelScope.launch {
            try {
                // Lade Benutzer aus dem Repository
                val wordsList = repository.getWords()
                _words.value = wordsList
            } catch (e: Exception) {
                // Fehlerbehandlung
                Log.e("UserViewModel", "Fehler beim Laden der Benutzer: ${e.localizedMessage}")
            }
        }
    }

    fun onAddWord(newWord: String) {
        viewModelScope.launch {
            try {
                val result = repository.addNewWord(newWord)
                // Erfolgreiches Hinzufügen des Benutzers
                loadWords()  // Benutzerliste aktualisieren
            } catch (e: Exception) {
                Log.e("ManagementUserViewModel", "Fehler beim Hinzufügen des Benutzers: ${e.localizedMessage}")
            }
        }
    }

    fun onDeleteWord(wordId: Int) {
        viewModelScope.launch {
            try {
                val result = repository.deleteWord(wordId)
                // Erfolgreiches Hinzufügen des Benutzers
                loadWords()  // Benutzerliste aktualisieren
            } catch (e: Exception) {
                Log.e("ManagementUserViewModel", "Fehler beim Hinzufügen des Benutzers: ${e.localizedMessage}")
            }
        }
    }
}