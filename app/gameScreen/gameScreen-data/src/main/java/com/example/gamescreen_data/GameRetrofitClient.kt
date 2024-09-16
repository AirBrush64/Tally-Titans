package com.example.gamescreen_data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GameRetrofitClient {
    private const val BASE_URL = "http://192.168.2.168:8000" // Deine API-Base-URL

    // Lazy initialisiert die Retrofit-Instanz erst bei der ersten Verwendung
    val apiService: GameApiInterface.ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // JSON-Converter
            .build()
            .create(GameApiInterface.ApiService::class.java)
    }
}