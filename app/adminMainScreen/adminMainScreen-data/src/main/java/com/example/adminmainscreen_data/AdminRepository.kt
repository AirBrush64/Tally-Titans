package com.example.usermainscreen_data

class AdminRepository(private val remoteDataSource: AdminRemoteDataSource) {

    suspend fun login(username: String, password: String): Result<AdminApiInterface.AdminResponse> {
        return try {
            // Anfrage an den Remote Data Source und Rückgabe des Resultats
            val response = remoteDataSource.login(username, password)
            Result.success(response) // Erfolg zurückgeben
        } catch (e: Exception) {
            Result.failure(e) // Bei Fehler ein Failure-Result zurückgeben
        }
    }
}