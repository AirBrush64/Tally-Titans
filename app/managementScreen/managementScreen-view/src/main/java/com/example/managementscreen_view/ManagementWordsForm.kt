package com.example.managementscreen_view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ManagementWordsForm(
    word: String,
    onWordChange: (String) -> Unit,
    onAddWord: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Add New Word", style = MaterialTheme.typography.titleMedium)

        // Eingabefeld für den Benutzernamen
        OutlinedTextField(
            value = word,
            onValueChange = onWordChange,
            label = { Text("Word") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button zum Speichern des neuen Benutzers
        Button(
            onClick = onAddWord,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Word")
        }
    }
}
