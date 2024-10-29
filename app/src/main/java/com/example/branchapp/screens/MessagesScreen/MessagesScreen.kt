package com.example.branchapp.screens.MessagesScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.branchapp.model.Message.Message

@Composable
fun MessagesScreen(navController: NavController, authTokenArg: String,
                   viewModel: MessageScreenViewModel= hiltViewModel()){

//    Text(authTokenArg)

    val authToken=authTokenArg
//    if(viewModel.isLoading == true ){
//        CircularProgressIndicator()
//    }
    LaunchedEffect(authTokenArg) {
        viewModel.fetchAllMessages(authTokenArg)
    }
    var isLoading:Boolean =viewModel.isLoading
    var messageList=viewModel.list
    var errorMessage: String? =viewModel.errorMessage // State for error message
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) { 
        Spacer(modifier = Modifier.padding(25.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Messages",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (!errorMessage.isNullOrEmpty()) {
            Text(text = errorMessage!!, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn {
                items(messageList) {
                    MessageThreadItem(message= it) { }
                }
            }
        }
    }

}


@Composable
fun MessageThreadItem(message: Message, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(text = "Sender ID: ${message.agent_id ?: message.user_id}")
        Text(text = "Message: ${message.body}")
        Text(text = "Timestamp: ${message.timestamp}")
    }
}