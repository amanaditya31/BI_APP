package com.example.branchapp.screens.ConversationScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchapp.data.Resource
import com.example.branchapp.model.Message.Message
import com.example.branchapp.repository.BranchAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConversationScreenViewModel @Inject constructor(private val repository: BranchAppRepository)
    : ViewModel() {
    var isLoading: Boolean by mutableStateOf(false)
    var messages: List<Message> by mutableStateOf(emptyList())
    var errorMessage: String? by mutableStateOf(null)

    /**
     * Sends a message in a specific conversation.
     * @param authToken - Authorization token for API access.
     * @param threadId - ID of the conversation thread.
     * @param body - Message content to send.
     */
    fun sendMessage(authToken: String, threadId: Int, body: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Start loading on the Main thread for UI updates
            withContext(Dispatchers.Main) { isLoading = true }

            try {
                val response = repository.sendMessage(authToken, threadId, body)

                // Handle response based on its type
                withContext(Dispatchers.Main) {
                    when (response) {
                        is Resource.Success -> {
                            response.data?.let { newMessage ->
                                messages = messages + newMessage
                            }
                            errorMessage = null // Clear any previous errors
                        }
                        is Resource.Error -> {
                            // Log or display error message
                            withContext(Dispatchers.Main) {
                                errorMessage = response.message
                                isLoading = false
                                Log.e("SendMessage", "Couldn't send message: ${response.message}")
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                errorMessage = "Unexpected error occurred."
                                isLoading = false
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    errorMessage = "An error occurred: ${e.localizedMessage}"
                }
            } finally {
                withContext(Dispatchers.Main) { isLoading = false }
            }
        }
    }

}