package com.example.managementscreen_data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ManagementRetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8000" // API BASE URL

    // Initialisierung des Retrofit Client (Benutzer zum Zugreiffen auf API)
    // Lazy initialisiert die Retrofit-Instanz erst bei der ersten Verwendung
    val apiService: ManagementApiInterface.ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // JSON-Converter
            .build()
            .create(ManagementApiInterface.ApiService::class.java)
    }
}