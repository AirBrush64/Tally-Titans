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

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val _registerResult = MutableStateFlow<Result<RegisterApiInterface.RegisterResponse>?>(null)
    val loginResult = _registerResult.asStateFlow()

    // Methoden zum Aktualisieren der Eingabefelder
    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

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