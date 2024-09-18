package com.example.managementscreen_data

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ManagementApiInterface {
    data class UsersResponse(
        val user_id: Int,
        val username: String,
        val email: String,
        val password: String,
        val role: String,
        val is_approved: Boolean
    )

    data class NewUserRequest(
        val username: String,
        val email: String,
        val password: String,
    )

    data class NewUserResponse(
        val message: String
    )

    data class UpdateUserRequest(
        val user_id: String,
        val username: String,
        val email: String,
        val password: String,
    )

    data class UpdateUserResponse(
        val message: String
    )

    data class DeleteUserResponse(
        val message: String
    )

    data class ApproveRequest(
        val user_id: String,
        val is_approved: Boolean
    )

    data class ApproveUserResponse(
        val message: String
    )

    data class ChangeRoleRequest(
        val user_id: String,
        val role: String
    )

    data class ChangeRoleResponse(
        val detail: String
    )

    data class WordsResponse(
        val word_id: Int,
        val word: String
    )

    data class NewWordRequest(
        val word: String
    )

    data class NewWordResponse(
        val message: String
    )


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