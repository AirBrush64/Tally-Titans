package com.example.gamescreen_data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GameApiInterface {

    // Kombinierte Antwort für Countdown und Wort
    data class GameInfoResponse(
        val timeLeft: Int,
        val word: String,
        val highscore: Int
    )

    // Datenklasse für die Punkteberechnung
    data class ScoreCalculationRequest(
        val user_id: String,
        val word_length: Int,
        val tries: Int,
        val time_left: Int
    )

    // Datenklasse für die Punkteberechnung
    data class ScoreCalculationResponse(
        val raw_points: Int,
        val multiplicator: Double,
        val points: Int,
        val new_highscore: Int
    )

    interface ApiService {
        // Neuer kombinierter Endpunkt für Countdown und Wort
        @GET("/gameinfo/")
        suspend fun getGameInfo(@Query("user_id") userId: String): GameInfoResponse

        @POST("/calculatepoints/")
        suspend fun calculatePoints(
            @Body scoreCalculationRequest: ScoreCalculationRequest
        ): ScoreCalculationResponse
    }
}
