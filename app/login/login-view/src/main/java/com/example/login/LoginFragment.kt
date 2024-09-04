package com.example.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.login_viewmodel.LoginViewModel
import com.example.login_viewmodel.LoginViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory()),
    navController: NavHostController
) {
    // Den UI-State vom ViewModel abrufen
    val username by loginViewModel.username.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()
    val loginResult by loginViewModel.loginResult.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentSize()
            ) {
                Text(
                    text = "Log in to your account",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Benutzername-Eingabe
                OutlinedTextField(
                    value = username,
                    onValueChange = { loginViewModel.onUsernameChanged(it) },
                    label = { Text("Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )

                // Passwort-Eingabe
                OutlinedTextField(
                    value = password,
                    onValueChange = { loginViewModel.onPasswordChanged(it) },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    )
                )

                // Login Button
                Button(
                    onClick = {
                        loginViewModel.login()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Log In")
                    }
                }

                // Anzeigen des Login-Ergebnisses
                loginResult?.let { result ->
                    when {
                        result.isSuccess -> {
                            Text(
                                text = "Login erfolgreich!",
                                modifier = Modifier.padding(top = 16.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            navController.navigate("home")
                        }
                        result.isFailure -> {
                            Text(
                                text = "Login fehlgeschlagen: ${result.exceptionOrNull()?.message}",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
