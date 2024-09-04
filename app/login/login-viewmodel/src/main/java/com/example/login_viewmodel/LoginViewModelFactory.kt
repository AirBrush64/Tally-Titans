package com.example.login_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.login_data.LoginRemoteDataSource
import com.example.login_data.LoginRepository
import com.example.login_data.LoginRetrofitClient

class LoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = LoginRetrofitClient.apiService
        val remoteDataSource = LoginRemoteDataSource(apiService)
        val repository = LoginRepository(remoteDataSource)

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
