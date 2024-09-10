package com.example.login_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login_data.LoginApiInterface
import com.example.login_data.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _loginResult = MutableStateFlow<Result<LoginApiInterface.LoginResponse>?>(null)
    val loginResult = _loginResult.asStateFlow()

    // Benutzername aktualisieren
    fun onEmailChanged(newUsername: String) {
        _username.value = newUsername
    }

    // Passwort aktualisieren
    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    // Login-Funktion aufrufen
    fun login() {
        _isLoading.value = true
        viewModelScope.launch {
            delay(3000)
            val result = repository.login(username.value, password.value)
            _loginResult.value = result
            _isLoading.value = false
        }
    }
}
