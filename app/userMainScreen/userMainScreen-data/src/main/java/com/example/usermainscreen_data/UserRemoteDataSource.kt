package com.example.usermainscreen_data

class UserRemoteDataSource(private val apiService: UserApiInterface.ApiService) {

    suspend fun highscore(): List<UserApiInterface.HighscoreResponse> {
        // Anfrage an die API mit den übergebenen Benutzerdaten
        return apiService.getHighscores()
    }
}