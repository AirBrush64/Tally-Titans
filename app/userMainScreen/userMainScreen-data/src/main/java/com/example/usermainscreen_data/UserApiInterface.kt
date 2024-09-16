package com.example.usermainscreen_data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiInterface {
    data class HighscoreResponse(
        val username: String,
        val highscore: Int
    )

    interface ApiService {
        @GET("/highscores/")
        suspend fun getHighscores(): List<HighscoreResponse>
    }
}