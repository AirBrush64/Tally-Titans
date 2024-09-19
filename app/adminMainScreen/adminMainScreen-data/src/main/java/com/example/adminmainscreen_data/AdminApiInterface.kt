package com.example.usermainscreen_data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AdminApiInterface {
    // Highscore Response mit Benutzername und Passwort
    data class HighscoreResponse(
        val username: String,
        val highscore: Int
    )

    // Endpunkte für API calls
    interface ApiService {
        @GET("/highscores/")
        suspend fun getHighscores(): List<HighscoreResponse>
    }
}