package com.example.managementscreen_view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.managementscreen_data.ManagementApiInterface
import com.example.managementscreen_viewmodel.ManagementUserViewModelFactory
import com.example.managementscreen_viewmodel.ManagementUserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementUserScreen(
    viewModel: ManagementUserViewModel = viewModel(factory = ManagementUserViewModelFactory()),
    navController: NavController
) {
    // Zustand für das BottomSheet und die Coroutine
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )
    val scope = rememberCoroutineScope()

    // State-Variablen für die Benutzereingaben
    var newUsername by remember { mutableStateOf("") }
    var newEmail by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }  // Zustand, ob der Bearbeitungsmodus aktiv ist
    var editUserId by remember { mutableStateOf("") }  // Speichert die user_id des zu bearbeitenden Benutzers

    // Benutzerliste aus dem ViewModel beobachten
    val users by viewModel.users.collectAsState()

    // Benutzerliste laden, wenn der Screen angezeigt wird
    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    // BottomSheetScaffold zur Anzeige der Benutzerliste
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            // Formular zum Hinzufügen oder Bearbeiten eines Benutzers
            ManagementUserForm(
                // Prefilling der Felder für Bearbeitung oder Neuanlage
                username = newUsername,
                email = newEmail,
                password = newPassword,
                onUsernameChange = { newUsername = it },
                onEmailChange = { newEmail = it },
                onPasswordChange = { newPassword = it },
                isEditing = isEditing,
                onAddUser = {
                    // Validierung vor dem Speichern
                    if (newUsername.isNotEmpty() && newEmail.isNotEmpty() && newPassword.isNotEmpty()) {
                        if (isEditing) {
                            // Bearbeitung speichern
                            viewModel.onEditUser(editUserId, newUsername, newEmail, newPassword)
                        } else {
                            // Neuer Benutzer speichern
                            viewModel.onAddNewUser(newUsername, newEmail, newPassword)
                        }

                        // Schließen des BottomSheets
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.hide()  // Schließt das BottomSheet
                        }
                    }
                }
            )
        },
        sheetPeekHeight = 0.dp,  // Höhe des BottomSheets, wenn es nicht aktiv ist
        topBar = {
            TopAppBar(
                title = { Text("User Management") },
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
                    // Benutzerliste anzeigen
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(users.reversed()) { user ->
                            UserItem(
                                user = user,
                                onEditClick = {
                                    // Benutzerdaten in das Formular laden und den Bearbeitungsmodus aktivieren
                                    newUsername = user.username
                                    newEmail = user.email
                                    newPassword = user.password ?: ""
                                    editUserId = user.user_id.toString()  // Setze die user_id für die Bearbeitung
                                    isEditing = true  // Aktiviere den Bearbeitungsmodus
                                    scope.launch {
                                        bottomSheetScaffoldState.bottomSheetState.expand()
                                    }
                                },
                                onDeleteClick = {
                                    viewModel.onDeleteUser(user.user_id)
                                },
                                onApproveClick = { isApproved ->
                                    viewModel.onApproveUser(user.user_id.toString(), user.is_approved)
                                },
                                onChangeRoleClick = {
                                    Log.d("ManagementUserScreen", "Change role clicked for user_id=${user.user_id}, role=${user.role}")
                                    viewModel.onChangeRole(user.user_id.toString(), user.role)
                                }
                            )
                        }
                    }

                    // FloatingActionButton zum Hinzufügen eines Benutzers
                    FloatingActionButton(
                        onClick = {
                            // Felder leeren und den Bearbeitungsmodus deaktivieren
                            newUsername = ""
                            newEmail = ""
                            newPassword = ""
                            isEditing = false  // Deaktiviere den Bearbeitungsmodus (Hinzufügen-Modus)
                            scope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()  // Öffnet das BottomSheet
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add User")
                    }
                }
            }
        }
    )
}


@Composable
fun UserItem(
    user: ManagementApiInterface.UsersResponse,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onApproveClick: (Boolean) -> Unit,
    onChangeRoleClick: (String) -> Unit  // Hinzugefügtes Callback für das Wechseln der Rolle
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
            // Benutzername mit Icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Username",
                    tint = textColor,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // E-Mail mit Icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = textColor,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Rolle mit Icon und Rolle wechseln
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Role",
                    tint = textColor,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = user.role,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor
                )

                Spacer(modifier = Modifier.weight(1f)) // Spacer für Abstand zwischen Text und Buttons
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Genehmigungsstatus mit Checkbox für Interaktivität
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Approved",
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor
                )
                Checkbox(
                    checked = user.is_approved,  // Setzt den initialen Zustand auf den Wert von user.is_approved
                    onCheckedChange = { onApproveClick(it) },  // Ruft das Callback auf, wenn der Status geändert wird
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Icon für das Ändern der Rolle, Bearbeiten und Löschen-Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Icon für Rollenwechsel (vor dem Editier-Icon)
                IconButton(
                    onClick = {
                        // Wechsle die Rolle entsprechend dem aktuellen Status
                        val newRole = if (user.role == "user") "admin" else "user"
                        onChangeRoleClick(newRole)  // Neue Rolle übergeben und Backend-Update auslösen
                    }
                ) {
                    Icon(
                        imageVector = if (user.role == "user") Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Change Role",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

                // Icon für Bearbeiten
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit User",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Icon für Löschen
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete User",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}



