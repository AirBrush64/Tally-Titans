package com.example.register_data

class RegisterRepository(private val remoteDataSource: RegisterRemoteDataSource) {
    suspend fun register(username: String, email: String, password: String): Result<RegisterApiInterface.RegisterResponse> {
        return try {
            // Anfrage an den Remote Data Source und Rückgabe des Resultats
            val response = remoteDataSource.register(username, email, password)
            Result.success(response) // Erfolg zurückgeben
        } catch (e: Exception) {
            Result.failure(e) // Bei Fehler ein Failure-Result zurückgeben
        }
    }
}