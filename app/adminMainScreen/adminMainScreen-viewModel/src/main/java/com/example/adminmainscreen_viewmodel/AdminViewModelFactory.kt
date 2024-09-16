package com.example.usermainscreen_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usermainscreen_data.AdminRemoteDataSource
import com.example.usermainscreen_data.AdminRepository
import com.example.usermainscreen_data.AdminRetrofitClient

class AdminViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = AdminRetrofitClient.apiService
        val remoteDataSource = AdminRemoteDataSource(apiService)
        val repository = AdminRepository(remoteDataSource)

        if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            return AdminViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
