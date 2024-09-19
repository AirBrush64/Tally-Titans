package com.example.usermainscreen_data

class UserRepository(private val remoteDataSource: UserRemoteDataSource) {

    suspend fun getHighscores(): Result<List<UserApiInterface.HighscoreResponse>> {
        return try {
            // Anfrage an den Remote Data Source und Rückgabe des Resultats
            val response = remoteDataSource.highscore()
            Result.success(response) // Bei Erfolg ein Erfolg Response
        } catch (e: Exception) {
            Result.failure(e) // Bei Fehler ein Failure-Result zurückgeben
        }
    }
}