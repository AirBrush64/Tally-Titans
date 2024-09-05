package com.example.register_data

import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiInterface {
    data class RegisterRequest(
        val username: String,
        val email: String,
        val password: String
    )

    data class RegisterResponse(
        val token: String,
        val success: Boolean
    )

    interface ApiService {
        @POST("/register/")
        suspend fun register(@Body request: RegisterRequest): RegisterResponse
    }
}
