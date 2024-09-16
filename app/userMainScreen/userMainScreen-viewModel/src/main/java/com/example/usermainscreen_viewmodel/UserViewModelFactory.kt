package com.example.usermainscreen_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usermainscreen_data.UserRemoteDataSource
import com.example.usermainscreen_data.UserRepository
import com.example.usermainscreen_data.UserRetrofitClient

class UserViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = UserRetrofitClient.apiService
        val remoteDataSource = UserRemoteDataSource(apiService)
        val repository = UserRepository(remoteDataSource)

        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
