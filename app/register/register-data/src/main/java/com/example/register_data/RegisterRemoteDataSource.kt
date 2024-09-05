package com.example.register_data

class RegisterRemoteDataSource(private val apiService: RegisterApiInterface.ApiService) {
    suspend fun register(username: String, email: String, password: String): RegisterApiInterface.RegisterResponse {
        // Anfrage an die API mit den übergebenen Benutzerdaten
        return apiService.register(RegisterApiInterface.RegisterRequest(username, email, password))
    }
}