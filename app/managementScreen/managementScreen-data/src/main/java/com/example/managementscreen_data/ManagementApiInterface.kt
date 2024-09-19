package com.example.managementscreen_data

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ManagementApiInterface {

    // User Response um auf jeweilige Daten zuzugreifen und zu verarbeiten
    data class UsersResponse(
        val user_id: Int,
        val username: String,
        val email: String,
        val password: String,
        val role: String,
        val is_approved: Boolean
    )
    // New User Request um neuen User hinzuzufügen
    data class NewUserRequest(
        val username: String,
        val email: String,
        val password: String,
    )

    // New User Response um Erfolg oder Failure zu bekommen
    data class NewUserResponse(
        val message: String
    )

    // New User Response um Erfolg oder Failure zu bekommen
    data class UpdateUserRequest(
        val user_id: String,
        val username: String,
        val email: String,
        val password: String,
    )

    // Update User Response um Erfolg oder Failure zu bekommen
    data class UpdateUserResponse(
        val message: String
    )

    // Delete User Response um Erfolg oder Failure zu bekommen
    data class DeleteUserResponse(
        val message: String
    )

    // Approve request um User zum approven
    data class ApproveRequest(
        val user_id: String,
        val is_approved: Boolean
    )

    // Approve Response um Erfolg oder Failure zu bekommen
    data class ApproveUserResponse(
        val message: String
    )

    // Change Role Request um Benutzer mit eigener ID zum Admin oder zum User zu machen
    data class ChangeRoleRequest(
        val user_id: String,
        val role: String
    )

    // Change Role Response um Erfolg oder Failure zu bekommen
    data class ChangeRoleResponse(
        val detail: String
    )

    //Word Response für die Wörterliste
    data class WordsResponse(
        val word_id: Int,
        val word: String
    )

    // NewWordRequest für neues Wort hinzufügen
    data class NewWordRequest(
        val word: String
    )

    // NewWordResponse um Erfolg oder Failure zu bekommen
    data class NewWordResponse(
        val message: String
    )


    //API ENDPUNKTE
    interface ApiService {
        @GET("/users/")
        suspend fun getUsers(): List<UsersResponse>

        @GET("/words/")
        suspend fun getWords(): List<WordsResponse>

        @POST("/register/")
        suspend fun addUser(@Body user: NewUserRequest): NewUserResponse

        @POST("/addword/")
        suspend fun addWord(@Body word: NewWordRequest): NewWordResponse

        @PUT("/updateusr/")
        suspend fun updateUser(@Body user: UpdateUserRequest): UpdateUserResponse

        @DELETE("/deleteusr/{user_id}")
        suspend fun deleteUser(@Path("user_id") userId: Int): DeleteUserResponse

        @DELETE("/deleteword/{word_id}")
        suspend fun deleteWord(@Path("word_id") wordId: Int): DeleteUserResponse

        @PUT("/approve/")
        suspend fun approveUser(@Body approveRequest: ApproveRequest): ApproveUserResponse

        @PUT("/changerole/")
        suspend fun changerRole(@Body changeRoleRequest: ChangeRoleRequest): ChangeRoleResponse
    }
}