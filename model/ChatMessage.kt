package com.example.calatour.model

import java.time.LocalDateTime

class ChatMessage (
    val author: String,
    val content: String,
    val timestamp: String,
    var important: Boolean = false
)