package com.example.login_data

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiInterface {
    // Login Request zum übergeben der email und passwort an das Backend
    data class LoginRequest(
        val email: String,
        val password: String
    )

    // Neue Datenklasse, die den Benutzer enthält
    data class User(
        val user_id: Int,
        val username: String,
        val highscore: Int,
        val role: String,
        val is_approved: Boolean,
        val email: String,
        val password_hash: String
    )

    // Aktualisierte LoginResponse-Datenklasse
    data class LoginResponse(
        val message: String,
        val user: User  // Benutzer-Objekt
    )

    interface ApiService {
        @POST("/login/")
        suspend fun login(@Body request: LoginRequest): LoginResponse
    }
}