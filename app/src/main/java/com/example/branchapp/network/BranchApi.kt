package com.example.branchapp.network

import com.example.branchapp.model.LoginRequest.AuthTokenResponse
import com.example.branchapp.model.LoginRequest.LoginRequest
import com.example.branchapp.model.Message.Message
import com.example.branchapp.model.NewMessageRequest.NewMessageRequest
import com.example.branchapp.model.ResetResponse.ResetResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface BranchApi {
    @POST("api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): AuthTokenResponse

    @GET("api/messages")
    suspend fun getAllMessages(
        @Header("X-Branch-Auth-Token") authToken: String
    ): List<Message>

    @POST("api/messages")
    suspend fun createMessage(
        @Header("X-Branch-Auth-Token") authToken: String,
        @Body newMessageRequest: NewMessageRequest
    ): Message

    @POST("api/reset")
    suspend fun resetMessages(
        @Header("X-Branch-Auth-Token") authToken: String
    ): ResetResponse
}