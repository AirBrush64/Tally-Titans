package com.example.usermainscreen_data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRetrofitClient {
    private const val BASE_URL = "http://airbrushdns.duckdns.org:8000" // Deine API-Base-URL

    // Lazy initialisiert die Retrofit-Instanz erst bei der ersten Verwendung
    val apiService: UserApiInterface.ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // JSON-Converter
            .build()
            .create(UserApiInterface.ApiService::class.java)
    }
}