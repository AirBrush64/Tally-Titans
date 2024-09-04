package com.example.login_data

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiInterface {
    data class LoginRequest(
        val email: String,
        val password: String
    )

    data class LoginResponse(
        val token: String,
        val success: Boolean
    )

    interface ApiService {
        @POST("/login/")
        suspend fun login(@Body request: LoginRequest): LoginResponse
    }
}