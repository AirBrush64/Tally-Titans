package com.example.managementscreen_data

import android.util.Log

class ManagementRemoteDataSource(private val apiService: ManagementApiInterface.ApiService) {

    // Abrufen der Liste von Benutzern aus der API
    suspend fun users(): List<ManagementApiInterface.UsersResponse> {
        return try {
            val response = apiService.getUsers()
            Log.d("RemoteDataSource", "Benutzerliste erfolgreich empfangen: $response")
            response
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Fehler beim Abrufen der Benutzer: ${e.localizedMessage}")
            emptyList() // Gibt eine leere Liste zurück im Fehlerfall
        }
    }

    // Abrufen der Liste von Wörtern aus der API
    suspend fun words(): List<ManagementApiInterface.WordsResponse> {
        return try {
            val response = apiService.getWords()
            Log.d("RemoteDataSource", "Wortliste erfolgreich empfangen: $response")
            response
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Fehler beim Abrufen der Wortliste: ${e.localizedMessage}")
            emptyList() // Gibt eine leere Liste zurück im Fehlerfall
        }
    }

    // Abrufen zum hinzufügen eines Benutzers
    suspend fun addNewUser(newUserRequest: ManagementApiInterface.NewUserRequest): ManagementApiInterface.NewUserResponse {
        return try {
            val response = apiService.addUser(newUserRequest)
            Log.d("RemoteDataSource", "Neuer Benutzer erfolgreich hinzugefügt: $response")
            response
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Fehler beim Hinzufügen des Benutzers: ${e.localizedMessage}")
            throw e  // Exception weiterwerfen, um das Problem an die aufrufende Stelle weiterzugeben
        }
    }

    // Abrufen um ein neues Wort hinzuzufügen
    suspend fun addNewWord(word: ManagementApiInterface.NewWordRequest): ManagementApiInterface.NewWordResponse {
        return try {
            val response = apiService.addWord(word)  // Direkt das übergebene 'word' verwenden
            Log.d("RemoteDataSource", "Neues Word erfolgreich hinzugefügt: $response")
            response
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Fehler beim Hinzufügen des Wortes: ${e.localizedMessage}")
            throw e  // Exception weiterwerfen, um das Problem an die aufrufende Stelle weiterzugeben
        }
    }

    // Aktualisiert einen bestehenden Benutzer
    suspend fun updateUser(updateUserRequest: ManagementApiInterface.UpdateUserRequest): ManagementApiInterface.UpdateUserResponse {
        return try {
            val response = apiService.updateUser(updateUserRequest)
            Log.d("RemoteDataSource", "Neuer Benutzer erfolgreich hinzugefügt: $response")
            response
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Fehler beim Hinzufügen des Benutzers: ${e.localizedMessage}")
            throw e  // Exception weiterwerfen, um das Problem an die aufrufende Stelle weiterzugeben
        }
    }

    // Löscht einen Benutzer anhand der user_id
    suspend fun deleteUser(userid: Int): ManagementApiInterface.DeleteUserResponse {
        return try {
            val response = apiService.deleteUser(userid)
            Log.d("RemoteDataSource", "Neuer Benutzer erfolgreich gelöscht: $response")
            response
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Fehler beim Löschen des Benutzers: ${e.localizedMessage}")
            throw e  // Exception weiterwerfen, um das Problem an die aufrufende Stelle weiterzugeben
        }
    }

    // Löscht ein Wort anhand der word_id
    suspend fun deleteWord(wordId: Int): ManagementApiInterface.DeleteUserResponse {
        return try {
            val response = apiService.deleteWord(wordId)
            Log.d("RemoteDataSource", "Wort erfolgreich gelöscht: $response")
            response
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Fehler beim Löschen des Wortes: ${e.localizedMessage}")
            throw e  // Exception weiterwerfen, um das Problem an die aufrufende Stelle weiterzugeben
        }
    }

    // Approves oder disapproves einen Benutzer anhand der user_id
    suspend fun approveUser(user_id: String, is_approved: Boolean): ManagementApiInterface.ApproveUserResponse {
        return try {
            val response = apiService.approveUser(ManagementApiInterface.ApproveRequest(user_id, is_approved))
            Log.d("RemoteDataSource", "Benutzer erfolgreich approved: $response")
            response
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Fehler beim Approven des Benutzers: ${e.localizedMessage}")
            throw e  // Exception weiterwerfen, um das Problem an die aufrufende Stelle weiterzugeben
        }
    }

    // Ändert die Rolle eines Benutzers (z.B. von "user" zu "admin" oder umgekehrt)
    suspend fun changeRole(user_id: String, role: String): ManagementApiInterface.ChangeRoleResponse {
        return try {
            val response = apiService.changerRole(ManagementApiInterface.ChangeRoleRequest(user_id, role))
            Log.d("RemoteDataSource", "Rolle erfolgreich gewechselt: $response")
            response
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Fehler beim Wechseln der Rolle des Benutzers: ${e.localizedMessage}")
            throw e  // Exception weiterwerfen, um das Problem an die aufrufende Stelle weiterzugeben
        }
    }
}