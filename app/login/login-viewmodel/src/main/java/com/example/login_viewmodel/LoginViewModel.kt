package com.example.login_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    // Mutable state for username, password, and loading status
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Function to update the username
    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    // Function to update the password
    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    // Simulate a login process
    fun login() {
        // Start loading
        _isLoading.value = true

        // Simulate a login delay
        viewModelScope.launch {
            delay(2000) // Simulate network request
            // Stop loading
            _isLoading.value = false
        }
    }
}
