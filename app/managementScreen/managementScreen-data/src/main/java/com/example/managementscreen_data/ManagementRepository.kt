package com.example.managementscreen_data

import android.util.Log

class ManagementRepository(private val remoteDataSource: ManagementRemoteDataSource) {

    suspend fun getUsers(): List<ManagementApiInterface.UsersResponse> {
        return try {
            val response = remoteDataSource.users()
            Log.d("Repository", "Benutzerliste erfolgreich geladen: $response")  // Debugging hinzufügen
            response
        } catch (e: Exception) {
            Log.e("Repository", "Fehler beim Laden der Benutzer: ${e.localizedMessage}")
            emptyList()
        }
    }

    suspend fun getWords(): List<ManagementApiInterface.WordsResponse> {
        return try {
            val response = remoteDataSource.words()
            Log.d("Repository", "Wortliste erfolgreich geladen: $response")  // Debugging hinzufügen
            response
        } catch (e: Exception) {
            Log.e("Repository", "Fehler beim Laden der Wortliste: ${e.localizedMessage}")
            emptyList()
        }
    }

    suspend fun addNewUser(username: String, email: String, password: String): Result<ManagementApiInterface.NewUserResponse> {
        return try {
            val response = remoteDataSource.addNewUser(ManagementApiInterface.NewUserRequest(username, email, password))
            Log.d("Repository", "Neuer Benutzer erfolgreich hinzugefügt: $response")
            Result.success(response)  // Erfolg in ein Result-Objekt verpacken
        } catch (e: Exception) {
            Log.e("Repository", "Fehler beim Hinzufügen des Benutzers: ${e.localizedMessage}")
            Result.failure(e)  // Fehler in ein Result-Objekt verpacken
        }
    }

    suspend fun addNewWord(word: String): Result<ManagementApiInterface.NewWordResponse> {
        return try {
            val response = remoteDataSource.addNewWord(ManagementApiInterface.NewWordRequest(word))
            Log.d("Repository", "Neues Wort erfolgreich hinzugefügt: $response")
            Result.success(response)  // Erfolg in ein Result-Objekt verpacken
        } catch (e: Exception) {
            Log.e("Repository", "Fehler beim Hinzufügen des Wortes: ${e.localizedMessage}")
            Result.failure(e)  // Fehler in ein Result-Objekt verpacken
        }
    }

    suspend fun updateUser(userId: String, username: String, email: String, password: String): Result<ManagementApiInterface.UpdateUserResponse> {
        return try {
            val response = remoteDataSource.updateUser(ManagementApiInterface.UpdateUserRequest(userId, username, email, password))
            Log.d("Repository", "Benutzer wurde erfolgreich bearbeitet: $response")
            Result.success(response)  // Erfolg in ein Result-Objekt verpacken
        } catch (e: Exception) {
            Log.e("Repository", "Fehler beim Bearbeiten des Benutzers: ${e.localizedMessage}")
            Result.failure(e)  // Fehler in ein Result-Objekt verpacken
        }
    }

    // Wort löschen
    suspend fun deleteWord(wordId: Int) {
        remoteDataSource.deleteWord(wordId)
    }

    // Benutzer löschen
    suspend fun deleteUser(user_id: Int) {
        remoteDataSource.deleteUser(user_id)
    }

    // Benutzer approven
    suspend fun approveUser(user_id: String, is_approved: Boolean) {
        remoteDataSource.approveUser(user_id, is_approved)
    }

    // Rolle ändern
    suspend fun changeRole(user_id: String, role: String) {
        Log.d("ManagementRepository", "Repository changeRole called with user_id=$user_id, role=$role")
        remoteDataSource.changeRole(user_id, role)
    }
}