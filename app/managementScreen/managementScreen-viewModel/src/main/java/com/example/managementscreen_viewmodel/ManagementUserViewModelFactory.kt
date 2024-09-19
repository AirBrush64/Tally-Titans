package com.example.managementscreen_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.managementscreen_data.ManagementRemoteDataSource
import com.example.managementscreen_data.ManagementRepository
import com.example.managementscreen_data.ManagementRetrofitClient

// ViewModelFactory zum erstellen des ViewModels mit cutom parameter
class ManagementUserViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = ManagementRetrofitClient.apiService
        val remoteDataSource = ManagementRemoteDataSource(apiService)
        val repository = ManagementRepository(remoteDataSource)

        if (modelClass.isAssignableFrom(ManagementUserViewModel::class.java)) {
            return ManagementUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



