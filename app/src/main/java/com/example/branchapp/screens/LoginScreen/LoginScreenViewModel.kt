package com.example.branchapp.screens.LoginScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchapp.data.Resource
import com.example.branchapp.repository.BranchAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor( private val repository: BranchAppRepository)
    : ViewModel(){

    var isLoading:Boolean by mutableStateOf( true)
    var authToken : String? by mutableStateOf(null)
    var errorMessage: String? by mutableStateOf(null) // State for error message

    fun loginUser(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (username.isEmpty() || password.isEmpty()) {
                withContext(Dispatchers.Main) {
                    errorMessage = "Username or password is empty"
                }
                return@launch
            }

            withContext(Dispatchers.Main) { isLoading = true }
            try {
                when (val response = repository.loginUser(username, password)) {
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            authToken = response.data?.auth_token
                            isLoading = false
                            if (authToken.isNullOrEmpty()) {
                                errorMessage = "Received empty auth token"
                            } else {
                                Log.d("Login", "Login successful with token: $authToken")
                            }
                        }
                    }
                    is Resource.Error -> {
                        // Log or display error message
                        withContext(Dispatchers.Main) {
                            errorMessage = response.message
                            isLoading = false
                            Log.e("Login", "Failed login: ${response.message}")
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
                    isLoading = false
                    errorMessage = "An error occurred: ${exception.message}"
                    Log.e("Login", "loginUser: ${exception.message.toString()}")
                }
            }
        }
    }

}