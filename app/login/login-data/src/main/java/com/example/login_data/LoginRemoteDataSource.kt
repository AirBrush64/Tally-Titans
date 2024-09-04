package com.example.login_data

class LoginRemoteDataSource(private val apiService: LoginApiInterface.ApiService) {

    suspend fun login(username: String, password: String): LoginApiInterface.LoginResponse {
        // Anfrage an die API mit den übergebenen Benutzerdaten
        return apiService.login(LoginApiInterface.LoginRequest(username, password))
    }
}