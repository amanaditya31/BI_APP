package com.example.branchapp.model.Message


data class Message(
    val id: Int,
    val thread_id: Int,
    val user_id: Int,
    val agent_id: Int?,
    val body: String,
    val timestamp: String
)
