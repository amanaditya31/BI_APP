package com.example.branchapp.screens.MessagesScreen

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
class MessageScreenViewModel @Inject constructor( private val repository: BranchAppRepository)
    : ViewModel() {
    var isLoading:Boolean by mutableStateOf( true)
//    var authToken : String? by mutableStateOf(null)
    var list: List<Message> by mutableStateOf(listOf())
    var errorMessage: String? by mutableStateOf(null) // State for error message

    fun fetchAllMessages(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (authToken.isNullOrEmpty()) {
                Log.e("Messages", "AuthToken is empty")
                return@launch
            }

            withContext(Dispatchers.Main) { isLoading = true }
            try {
                when (val response = repository.fetchAllMessages(authToken)) {
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            list = response.data!!
                                .groupBy { it.thread_id }
                                .mapValues { it.value.maxByOrNull { msg -> msg.timestamp } }
                                .values.filterNotNull()
                            if(list.isNotEmpty()){
                                // Handle successful login (e.g., store auth token or navigate)
                                Log.d("Login", "Successfully fetched messages $authToken")
                                isLoading = false
                            }
                        }
                    }
                    is Resource.Error -> {
                        // Log or display error message
                        withContext(Dispatchers.Main) {
                            errorMessage = response.message
                            Log.e("Login", "Failed to fetch messages: ${response.message}")
                            isLoading = false
                        }
                    }
                    else -> {
                        withContext(Dispatchers.Main) {
                            errorMessage = "Unexpected error occurred."
                            isLoading = false
                        }
                    }
                }
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    errorMessage = "An error occurred: ${exception.message}"
                    isLoading = false
                }
            }
        }
    }
}