package com.example.adriavalidationtest.models

import com.google.firebase.Timestamp
import java.util.*

class Message(val id: String, val text: String, val source: String, val destination: String, val timestamp: Timestamp) {
    constructor() : this("", "", "", "", Timestamp(Date()))
}