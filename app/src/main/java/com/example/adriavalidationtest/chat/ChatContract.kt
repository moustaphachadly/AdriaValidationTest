package com.example.adriavalidationtest.chat

import com.example.adriavalidationtest.models.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class ChatContract {

    interface View {
        fun emptyField()
        fun messageSent()
//        fun adapterClickListener(item: UserItem, view: android.view.View)
        fun messageAdded(groupAdapter: GroupAdapter<ViewHolder>)
//        fun usersListenerFailed(exception: Exception)
    }

    interface Presenter {
        fun listenForMessages(friend: User)
        fun sendMessage(text: String)
    }

}