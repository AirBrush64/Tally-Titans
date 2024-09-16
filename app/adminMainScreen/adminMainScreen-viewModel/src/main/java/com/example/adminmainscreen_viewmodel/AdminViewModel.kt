package com.example.usermainscreen_viewmodel

import androidx.lifecycle.ViewModel
import com.example.usermainscreen_data.AdminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdminViewModel(private val repository: AdminRepository) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Benutzername aktualisieren
    fun onEmailChanged(newUsername: String) {
        _username.value = newUsername
    }

    // Passwort aktualisieren
    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }
}
