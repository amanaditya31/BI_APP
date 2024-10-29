package com.example.branchapp.navigation

enum class AppScreens {
    LoginScreen,
    MessagesScreen,
    ConversationScreen;

    companion object {
        fun fromRoute(route: String?): AppScreens = when (route?.substringBefore("/")) {
            LoginScreen.name -> LoginScreen
            MessagesScreen.name-> MessagesScreen
            ConversationScreen.name->ConversationScreen
            null ->  MessagesScreen
            else -> throw IllegalArgumentException("Route is ${route} is not recognized")
        }
    }

}