package com.example.gamescreen_data

import android.content.Context
import android.util.Log

class GameRepository(private val remoteDataSource: GameRemoteDataSource) {

    suspend fun getGameInfo(userId: String): Result<GameApiInterface.GameInfoResponse> {
        return try {
            // Anfrage an den Remote Data Source mit user_id und Rückgabe des Resultats
            val response = remoteDataSource.fetchGameInfo(userId)
            Result.success(response)  // Erfolg mit dem kombinierten GameInfoResponse zurückgeben
        } catch (e: Exception) {
            Result.failure(e)  // Bei Fehler ein Failure-Result zurückgeben
        }
    }

    // Funktion zur Berechnung der Punkte
    suspend fun calculatePoints(
        userId: String,
        wordLength: Int,
        tries: Int,
        timeLeft: Int
    ): Result<GameApiInterface.ScoreCalculationResponse> {
        return try {
            val response = remoteDataSource.calculatePoints(userId, wordLength, tries, timeLeft)
            Result.success(response)  // Erfolg zurückgeben
        } catch (e: Exception) {
            Result.failure(e)  // Fehler weitergeben
        }
    }
}
