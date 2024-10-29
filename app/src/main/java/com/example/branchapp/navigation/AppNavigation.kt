package com.example.branchapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.branchapp.screens.ConversationScreen.ConversationScreen
import com.example.branchapp.screens.ConversationScreen.ConversationScreenViewModel
import com.example.branchapp.screens.LoginScreen.LoginScreen
import com.example.branchapp.screens.LoginScreen.LoginScreenViewModel
import com.example.branchapp.screens.MessagesScreen.MessageScreenViewModel
import com.example.branchapp.screens.MessagesScreen.MessagesScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppNavigation(navController: NavHostController){
    NavHost (navController=navController, startDestination= AppScreens.LoginScreen.name){


        composable(AppScreens.LoginScreen.name){
            val loginScreenViewModel= hiltViewModel<LoginScreenViewModel>()
            LoginScreen(navController = navController, loginScreenViewModel)
        }

        val messagesScreen=AppScreens.MessagesScreen.name
        composable("$messagesScreen/{authToken}", arguments=listOf(
            navArgument("authToken"){type= NavType.StringType },
        )){ backStackEntry->
            val authToken= backStackEntry.arguments?.getString("authToken") ?: ""
            val messageScreenViewModel=hiltViewModel<MessageScreenViewModel>()
            MessagesScreen(
                navController = navController,
                authTokenArg = authToken,
                messageScreenViewModel
            )
        }

        val conversationScreen=AppScreens.ConversationScreen.name
        composable("$conversationScreen/{authToken}/{message}", arguments=listOf(
            navArgument("authToken"){type= NavType.StringType },
            navArgument("message"){type= NavType.StringType },
        )){ backStackEntry->
            val authToken= backStackEntry.arguments?.getString("authToken") ?: ""
            val message= backStackEntry.arguments?.getString("message") ?: ""

            val conversationScreenViewModel=hiltViewModel<ConversationScreenViewModel>()
            ConversationScreen(navController = navController, conversationScreenViewModel, authTokenArg = authToken, message = message)

        }

    }
}