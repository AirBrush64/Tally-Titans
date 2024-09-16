package com.example.usermainscreen_data

class UserRemoteDataSource(private val apiService: UserApiInterface.ApiService) {

    suspend fun login(username: String, password: String): UserApiInterface.LoginResponse {
        // Anfrage an die API mit den übergebenen Benutzerdaten
        return apiService.login(UserApiInterface.LoginRequest(username, password))
    }
}