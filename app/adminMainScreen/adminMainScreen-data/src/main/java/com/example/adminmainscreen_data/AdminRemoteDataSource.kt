package com.example.usermainscreen_data

class AdminRemoteDataSource(private val apiService: AdminApiInterface.ApiService) {

    suspend fun login(username: String, password: String): AdminApiInterface.AdminResponse {
        // Anfrage an die API mit den übergebenen Benutzerdaten
        return apiService.login(AdminApiInterface.LoginRequest(username, password))
    }
}