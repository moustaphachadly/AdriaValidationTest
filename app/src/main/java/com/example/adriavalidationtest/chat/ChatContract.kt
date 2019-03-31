package com.example.adriavalidationtest.chat

import com.example.adriavalidationtest.models.User

class ChatContract {

    interface View {
        fun emptyField()
        fun messageSent()
    }

    interface Presenter {
        fun sendMessage(text: String)
    }

}