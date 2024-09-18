package com.example.managementscreen_view

import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ManagementUserForm(
    username: String,
    email: String,
    password: String,  // Passwortfeld bleibt erhalten
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,  // Passwortänderung bleibt erhalten
    onAddUser: () -> Unit,
    isEditing: Boolean  // Neuer Parameter für den Bearbeitungsmodus
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Dynamischer Titel basierend auf dem Modus
        Text(
            text = if (isEditing) "Edit User" else "Add New User",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Eingabefeld für den Benutzernamen
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Eingabefeld für die E-Mail
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Eingabefeld für das Passwort
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),  // Versteckt das Passwort
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button mit dynamischem Text
        Button(
            onClick = onAddUser,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditing) "Edit User" else "Add User")
        }
    }
}
