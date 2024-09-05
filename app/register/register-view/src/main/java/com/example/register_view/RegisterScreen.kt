package com.example.register_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.login.loginview.R
import com.example.register_viewmodel.RegisterViewModel
import com.example.register_viewmodel.RegisterViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory()),
    navController: NavController
) {
    // UI-State aus dem ViewModel abrufen
    val username by registerViewModel.username.collectAsState("")
    val email by registerViewModel.email.collectAsState("")
    val password by registerViewModel.password.collectAsState("")
    val isLoading by registerViewModel.isLoading.collectAsState()
    val registerResult by registerViewModel._registerResult.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Register") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.image_description),
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.TopCenter)
                    .size(175.dp)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentSize()
            ) {
                Text(
                    text = "Create a new account",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Benutzername-Eingabe
                OutlinedTextField(
                    value = username,
                    onValueChange = { registerViewModel.onUsernameChanged(it) },
                    label = { Text("Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                // E-Mail-Eingabe
                OutlinedTextField(
                    value = email,
                    onValueChange = { registerViewModel.onEmailChanged(it) },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email
                    )
                )

                // Passwort-Eingabe
                OutlinedTextField(
                    value = password,
                    onValueChange = { registerViewModel.onPasswordChanged(it) },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    )
                )

                // Register Button
                Button(
                    onClick = {
                        registerViewModel.register()
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
                        Text("Sign Up")
                    }
                }

                // Anzeigen des Registrierungsergebnisses
                registerResult?.let { result ->
                    when {
                        result.isSuccess -> {
                            Text(
                                text = "Registration successful!",
                                modifier = Modifier.padding(top = 16.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            navController.navigate("login")
                        }
                        result.isFailure -> {
                            Text(
                                text = "Registration failed: ${result.exceptionOrNull()?.message}",
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