package com.example.register_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.register_data.RegisterRemoteDataSource
import com.example.register_data.RegisterRepository
import com.example.register_data.RegisterRetrofitClient

// ViewModelFactory zum erstellen des ViewModels mit cutom parameter
class RegisterViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = RegisterRetrofitClient.apiService
        val remoteDataSource = RegisterRemoteDataSource(apiService)
        val repository = RegisterRepository(remoteDataSource)

        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
