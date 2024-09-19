package com.example.register_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.register_data.RegisterApiInterface
import com.example.register_data.RegisterRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {

    // Mutable State für Eingabewerte
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    //Variable zum Verarbeiten von Änderungen des Wertes
    private val _email = MutableStateFlow("")
    //Leesbare Variable für die View
    val email: StateFlow<String> = _email

    //Variable zum Verarbeiten von Änderungen des Wertes
    private val _password = MutableStateFlow("")
    //Leesbare Variable für die View
    val password: StateFlow<String> = _password

    //Variable zum Verarbeiten von Änderungen des Wertes
    private val _isLoading = MutableStateFlow(false)
    //Leesbare Variable für die View
    val isLoading: StateFlow<Boolean> = _isLoading

    //Variable zum Verarbeiten von Änderungen des Wertes
    val _registerResult = MutableStateFlow<Result<RegisterApiInterface.RegisterResponse>?>(null)
    //Leesbare Variable für die View
    val registerResult = _registerResult.asStateFlow()

    // Methode zum Aktualisieren des Username-Eingabe-Felds
    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }
    // Methode zum Aktualisieren des Email-Eingabe-Felds
    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }
    // Methode zum Aktualisieren des Passwort-Eingabe-Felds
    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    // Methode zum Registrieren des neuen Users
    fun register() {
        _isLoading.value = true
        viewModelScope.launch {
            delay(3000)
            val result = repository.register(username.value, email.value, password.value)
            _registerResult.value = result
            _isLoading.value = false
        }
    }
}