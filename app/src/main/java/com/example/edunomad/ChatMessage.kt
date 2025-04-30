package com.example.edunomad

data class ChatMessage(
    val sender: String,
    val message: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
