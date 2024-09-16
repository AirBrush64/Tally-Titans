package com.example.login_viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login_data.LoginApiInterface
import com.example.login_data.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context, private val repository: LoginRepository) : ViewModel() {

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
            delay(3000)  // Simuliert eine Wartezeit (kann entfernt werden)
            val result = repository.login(username.value, password.value)
            _loginResult.value = result

            result.onSuccess { loginResponse ->
                val userId = loginResponse.user.user_id
                val userRole = loginResponse.user.role
                Log.d("LoginViewModel", "User ID: $userId")
                saveUserToPreferences(userId, userRole)
            }

            _isLoading.value = false
        }
    }

    private fun saveUserToPreferences(userId: Int, userRole: String) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("user_id", userId)
            putString("user_role", userRole)
            apply()  // Asynchron speichern
        }
        Log.d("LoginViewModel", "User data saved: ID=$userId, Role=$userRole")
    }
}
