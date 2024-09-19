package com.example.usermainscreen_data

class AdminRemoteDataSource(private val apiService: AdminApiInterface.ApiService) {

    suspend fun highscore(): List<AdminApiInterface.HighscoreResponse> {
        // Anfrage an die API mit den übergebenen Benutzerdaten
        return apiService.getHighscores()
    }
}