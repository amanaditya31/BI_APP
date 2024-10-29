package com.example.branchapp.screens.ConversationScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.branchapp.model.Message.Message
import com.google.gson.Gson


@Composable
fun ConversationScreen(navController: NavController,
                       viewModel: ConversationScreenViewModel = hiltViewModel(),
                       authTokenArg : String,
                       message : String
){

    val messageRecieved= Gson().fromJson(message, Message ::class.java)
    var isLoading = viewModel.isLoading
    var messages = viewModel.messages + messageRecieved
    val errorMessage = viewModel.errorMessage
    var messageInput by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            if (!errorMessage.isNullOrEmpty()) {
                Text(text = errorMessage!!, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(messages) { message ->
                        MessageThreadItem(message = message)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = messageInput,
                        onValueChange = { messageInput = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Type your message...") }
                    )
                    Button(
                        onClick = {
                            if (messageInput.isNotBlank()) {
                                viewModel.sendMessage(authTokenArg,messageRecieved.thread_id, messageInput)
                                messageInput = "" // Clear the input field
                            }
                        }
                    ) {
                        Text(text = "Send")
                    }
                }
            }
        }
    }
}

@Composable
fun MessageThreadItem(message: Message) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(text = "Sender ID: ${message.agent_id ?: message.user_id}", fontWeight = FontWeight.Bold)
        Text(text = "Message: ${message.body}")
        Text(text = "Timestamp: ${message.timestamp}")
    }
}
