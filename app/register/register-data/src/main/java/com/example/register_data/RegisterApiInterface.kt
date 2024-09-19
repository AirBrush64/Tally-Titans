package com.example.register_data

import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiInterface {
    //Register Request um neuen User zu registrieren
    data class RegisterRequest(
        val username: String,
        val email: String,
        val password: String
    )

    // Antwort vom Backend => Wird zurzeit nicht verwendet, war aber als Error Handling geplant
    data class RegisterResponse(
        val token: String,
        val success: Boolean
    )

    interface ApiService {
        @POST("/register/")
        suspend fun register(@Body request: RegisterRequest): RegisterResponse
    }
}
