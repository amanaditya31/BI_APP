package com.example.branchapp.repository

import com.example.branchapp.data.Resource
import com.example.branchapp.model.LoginRequest.AuthTokenResponse
import com.example.branchapp.model.LoginRequest.LoginRequest
import com.example.branchapp.model.Message.Message
import com.example.branchapp.model.NewMessageRequest.NewMessageRequest
import com.example.branchapp.model.ResetResponse.ResetResponse
import com.example.branchapp.network.BranchApi
import javax.inject.Inject

class BranchAppRepository @Inject constructor( private val api: BranchApi){

    private var authToken : String = ""
    suspend fun loginUser(username: String, password: String): Resource<AuthTokenResponse>{
        return try {
            Resource.Loading(data=true)

            // Create the login request object with the username and reversed password
            val loginRequest = LoginRequest(username = username, password = password)
            // Make the API call
            val loginResponse = api.login(loginRequest)
            if(loginResponse.auth_token.isNotBlank()) Resource.Loading(data=false)
            // Wrap the response in a successful Result

            authToken= loginResponse.auth_token
            Resource.Success(data = loginResponse)
        } catch(exception: Exception){
            Resource.Error(message = exception.message.toString())
        } finally {
            // Ensure loading state is set to false
            Resource.Loading(data = false)
        }
    }


    suspend fun fetchAllMessages(authToken: String ): Resource<List<Message>> {
        return try {
            // Emit loading state
            Resource.Loading(data = true)
            // Make the API call
            val messages = api.getAllMessages(authToken)

            if(messages.isNotEmpty()) Resource.Loading(data=false)
            // Emit success state with the messages data
            Resource.Success(data = messages)
        } catch (exception: Exception) {
            // Emit error state with the exception message
            Resource.Error(message = exception.message.toString())
        } finally {
            // Ensure loading state is set to false
            Resource.Loading(data = false)
        }
    }

    suspend fun sendMessage(authToken: String, threadId: Int, body: String): Resource<Message> {
        return try {
            // Emit loading state
            Resource.Loading(data = true)

            // Create the new message request object
            val newMessageRequest = NewMessageRequest(thread_id = threadId, body = body)

            // Make the API call to create the message
            val messageResponse = api.createMessage(authToken, newMessageRequest)
            if(messageResponse.body.isNotEmpty()) Resource.Loading(data=false)
            // Emit success state with the created message data
            Resource.Success(data = messageResponse)
        } catch (exception: Exception) {
            // Emit error state with the exception message
            Resource.Error(message = exception.message.toString())
        } finally {
            // Ensure loading state is set to false
            Resource.Loading(data = false)
        }
    }

    suspend fun resetAllMessages(authToken: String): Resource<ResetResponse> {
        return try {
            // Emit loading state
            Resource.Loading(data = true)

            // Make the API call to reset messages
            val resetResponse = api.resetMessages(authToken)
            if(resetResponse.status.isNotEmpty()) Resource.Loading(data=false)
            // Emit success state with the reset response data
            Resource.Success(data = resetResponse)
        } catch (exception: Exception) {
            // Emit error state with the exception message
            Resource.Error(message = exception.message.toString())
        } finally {
            // Ensure loading state is set to false
            Resource.Loading(data = false)
        }
    }

}