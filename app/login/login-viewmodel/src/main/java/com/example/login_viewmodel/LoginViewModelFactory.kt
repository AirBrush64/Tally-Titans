package com.example.login_viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.login_data.LoginRemoteDataSource
import com.example.login_data.LoginRepository
import com.example.login_data.LoginRetrofitClient

// ViewModelFactory zum erstellen des ViewModels mit cutom parameter
class LoginViewModelFactory(
    private val context: Context  // Context wird hier übergeben
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = LoginRetrofitClient.apiService
        val remoteDataSource = LoginRemoteDataSource(apiService)
        val repository = LoginRepository(remoteDataSource)

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(context, repository) as T  // Context an LoginViewModel übergeben
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
