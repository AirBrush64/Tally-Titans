package com.example.usermainscreen_data

class UserRepository(private val remoteDataSource: UserRemoteDataSource) {

    suspend fun login(username: String, password: String): Result<UserApiInterface.LoginResponse> {
        return try {
            // Anfrage an den Remote Data Source und Rückgabe des Resultats
            val response = remoteDataSource.login(username, password)
            Result.success(response) // Erfolg zurückgeben
        } catch (e: Exception) {
            Result.failure(e) // Bei Fehler ein Failure-Result zurückgeben
        }
    }
}