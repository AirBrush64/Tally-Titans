package com.example.managementscreen_view

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.managementscreen_data.ManagementApiInterface
import com.example.managementscreen_viewmodel.ManagementWordViewModel
import com.example.managementscreen_viewmodel.ManagementWordViewModelFactory
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items

// Bottom Sheet für eingabe eines neuen Wortes
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementWordScreen(
    viewModel: ManagementWordViewModel = viewModel(factory = ManagementWordViewModelFactory()),
    navController: NavController
) {
    // Zustand für das BottomSheet und die Coroutine
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )
    val scope = rememberCoroutineScope()

    var newWord by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }

    // Wörterliste aus dem ViewModel beobachten
    val words by viewModel.words.collectAsState()

    // Prüfe, ob das BottomSheetState initialisiert ist
    LaunchedEffect(Unit) {
        viewModel.loadWords()  // Wörter laden
        Log.d("ManagementWordScreen", "Words loaded: $words")
    }

    // BottomSheetScaffold zur Anzeige der Wörterliste
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            ManagementWordsForm(
                word = newWord,
                onWordChange = { newWord = it },
                onAddWord = {
                    // Validierung vor dem Speichern
                    if (newWord.isNotEmpty()) {
                        // Wort speichern
                        viewModel.onAddWord(newWord)

                        // Schließen des BottomSheets
                        scope.launch {
                            Log.d("ManagementWordScreen", "Hiding BottomSheet")
                            bottomSheetScaffoldState.bottomSheetState.hide()  // Schließt das BottomSheet
                        }
                    } else {
                        Log.d("ManagementWordScreen", "Validation failed")
                    }
                }
            )
        },
        sheetPeekHeight = 0.dp,  // Höhe des BottomSheets, wenn es nicht aktiv ist
        topBar = {
            TopAppBar(
                title = { Text("Word Management") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Wörterliste anzeigen (umgekehrt, damit das neueste Wort oben steht)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(words.reversed()) { word ->  // Die Liste umdrehen
                            WordItem(
                                word = word,
                                onDeleteClick = {
                                    Log.d("ManagementWordScreen", "WordID: " + word.word_id )
                                    viewModel.onDeleteWord(word.word_id)
                                }
                            )
                        }
                    }

                    // FloatingActionButton zum Hinzufügen eines neuen Wortes
                    FloatingActionButton(
                        onClick = {
                            newWord = ""
                            newDescription = ""
                            scope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()  // Öffnet das BottomSheet
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Word")
                    }
                }
            }
        }
    )
}

@Composable
fun WordItem(
    word: ManagementApiInterface.WordsResponse,
    onDeleteClick: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()

    // Farbe der Boxen und Text basierend auf dem Modus
    val boxColor = if (isDarkTheme) Color.White else Color.Black
    val textColor = if (isDarkTheme) Color.Black else Color.White

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = boxColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Wort mit Icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Word",
                    tint = textColor,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = word.word,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Löschen-Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Word",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}