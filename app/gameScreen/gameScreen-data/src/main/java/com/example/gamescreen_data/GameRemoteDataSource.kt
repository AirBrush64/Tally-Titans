package com.example.gamescreen_data

import android.util.Log

class GameRemoteDataSource(private val apiService: GameApiInterface.ApiService) {

    // Abrufen der kombinierten Informationen (Countdown und Wort)
    suspend fun fetchGameInfo(userId: String): GameApiInterface.GameInfoResponse {
        return try {
            // Übergibt die userId an den Endpunkt als Query-Parameter
            val response = apiService.getGameInfo(userId)
            Log.d("GameRemoteDataSource", "Game Info erfolgreich abgerufen: Zeit: ${response.timeLeft}, Wort: ${response.word}")
            response  // Gibt die kombinierte GameInfoResponse zurück
        } catch (e: Exception) {
            Log.e("GameRemoteDataSource", "Fehler beim Abrufen der Spielinformationen: ${e.localizedMessage}")
            throw e  // Fehler weitergeben
        }
    }

    suspend fun calculatePoints(
        userId: String,
        wordLength: Int,
        tries: Int,
        timeLeft: Int
    ): GameApiInterface.ScoreCalculationResponse {
        return try {
            val request = GameApiInterface.ScoreCalculationRequest(
                user_id = userId,
                word_length = wordLength,
                tries = tries,
                time_left = timeLeft
            )
            val response = apiService.calculatePoints(request)
            Log.d("GameRemoteDataSource", "Punkte erfolgreich berechnet: ${response.points}")
            response
        } catch (e: Exception) {
            Log.e("GameRemoteDataSource", "Fehler bei der Punkteberechnung: ${e.localizedMessage}")
            throw e
        }
    }
}

