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

class ManagementUserViewModel(private val repository: ManagementRepository) : ViewModel() {

    // MutableStateFlow für die Liste der Benutzer
    private val _users = MutableStateFlow<List<ManagementApiInterface.UsersResponse>>(emptyList())
    val users: StateFlow<List<ManagementApiInterface.UsersResponse>> = _users.asStateFlow()

    // Mutable State für Eingabewerte
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val _registerResult = MutableStateFlow<Result<ManagementApiInterface.NewUserResponse>?>(null)
    val registerResult = _registerResult.asStateFlow()

    // Benutzerliste laden
    fun loadUsers() {
        viewModelScope.launch {
            try {
                // Lade Benutzer aus dem Repository
                val userList = repository.getUsers()
                _users.value = userList
            } catch (e: Exception) {
                // Fehlerbehandlung
                Log.e("UserViewModel", "Fehler beim Laden der Benutzer: ${e.localizedMessage}")
            }
        }
    }

    fun onAddNewUser(newUsername: String, newEmail: String, newPassword: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.addNewUser(newUsername, newEmail, newPassword)
                _isLoading.value = false
                // Erfolgreiches Hinzufügen des Benutzers
                loadUsers()  // Benutzerliste aktualisieren
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e("ManagementUserViewModel", "Fehler beim Hinzufügen des Benutzers: ${e.localizedMessage}")
            }
        }
    }

    fun onEditUser(userid: String, username: String, email: String, password: String) {
        viewModelScope.launch {
            repository.updateUser(userid, username, email, password)  // Update Logik in der Repository
            loadUsers()  // Aktualisiere die Liste nach dem Bearbeiten
        }
    }

    // Benutzer löschen
    fun onDeleteUser(userid: Int) {
        viewModelScope.launch {
            repository.deleteUser(userid)
            loadUsers()
        }
    }

    fun onApproveUser(user_id: String, is_approved: Boolean) {
        viewModelScope.launch {
            repository.approveUser(user_id, is_approved)
            loadUsers()
        }
    }

    fun onChangeRole(user_id: String, role: String) {
        Log.d("ManagementViewModel", "Repository changeRole called with user_id=$user_id, role=$role")
        viewModelScope.launch {
            repository.changeRole(user_id, role)
            loadUsers()
        }
    }
}
