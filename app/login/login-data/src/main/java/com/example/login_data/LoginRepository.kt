package com.example.login_data

class LoginRepository(private val remoteDataSource: LoginRemoteDataSource) {

    suspend fun login(username: String, password: String): Result<LoginApiInterface.LoginResponse> {
        return try {
            // Anfrage an den Remote Data Source und Rückgabe des Resultats
            val response = remoteDataSource.login(username, password)
            Result.success(response) // Erfolg zurückgeben
        } catch (e: Exception) {
            Result.failure(e) // Bei Fehler ein Failure-Result zurückgeben
        }
    }
}