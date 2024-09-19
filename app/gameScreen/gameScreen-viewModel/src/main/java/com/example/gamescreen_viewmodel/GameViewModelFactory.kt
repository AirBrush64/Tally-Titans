package com.example.usermainscreen_viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gamescreen_data.GameRemoteDataSource
import com.example.gamescreen_data.GameRepository
import com.example.gamescreen_data.GameRetrofitClient

// ViewModelFactory zum erstellen des ViewModels mit cutom parameter
class GameViewModelFactory(
    private val context: Context  // Context wird hier übergeben
) : ViewModelProvider.Factory {
    val apiService = GameRetrofitClient.apiService
    val remoteDataSource = GameRemoteDataSource(apiService)
    val repository = GameRepository(remoteDataSource)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



