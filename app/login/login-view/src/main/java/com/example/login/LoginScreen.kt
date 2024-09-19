package com.example.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.login_viewmodel.LoginViewModel
import com.example.login_viewmodel.LoginViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.login.loginview.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(LocalContext.current)),
    navController: NavController
) {
    // Den UI-State vom ViewModel abrufen
    val email by loginViewModel.username.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()
    val loginResult by loginViewModel.loginResult.collectAsState()

    //UserID wird hier gespeichert nach login, damit die Highscore Anzeige während des Spiels funktioniert
    val sharedPreferences = LocalContext.current.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    //Rolle wird hier gespeichert nach login, damit entschieden werden kann, ob die Person die Admin Ansicht bekommt oder die User Ansicht
    val userRole = sharedPreferences.getString("user_role", null)


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
            // Icon Anzeige
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.image_description),
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.TopCenter)
                    .size(175.dp)
            )
            // Spalte mit allen Views in richtiger Reinfolge
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentSize()
            ) {
                //Text für Login Account
                Text(
                    text = "Log in to your account",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Email-Eingabe
                OutlinedTextField(
                    value = email,
                    onValueChange = { loginViewModel.onEmailChanged(it) },
                    label = { Text("Email") },
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
                            Log.d("LoginScreen: ", "Rolle: $userRole")
                            if (userRole == "admin") {
                                navController.navigate("adminHome") {
                                    popUpTo("login") { inclusive = true }
                                    launchSingleTop = true
                                }
                            } else {
                                navController.navigate("userHome") {
                                    popUpTo("login") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
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

                // Sign Up Button
                TextButton(
                    onClick = {
                        navController.navigate("register") // Navigiere zur Registrierung
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Don't have an account? Sign Up")
                }
            }
        }
    }
}
