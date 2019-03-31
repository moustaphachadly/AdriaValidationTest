package com.example.adriavalidationtest.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.adriavalidationtest.R
import com.example.adriavalidationtest.models.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity(), ChatContract.View {

    var chatPresenter: ChatPresenter? = null
    var friend: User? = null
    var adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerview_chat.adapter = adapter
        friend = intent.getParcelableExtra<User>(User.USER_KEY)
        supportActionBar?.title = friend?.username

        //
        button_send.setOnClickListener {
            chatPresenter!!.sendMessage(edittext_chat.text.toString())
        }

        // Setup presenter
        if (chatPresenter == null) {
            chatPresenter = ChatPresenter(this, this)
        }

        friend?.let {
            chatPresenter!!.listenForMessages(it)
        }
    }

    override fun emptyField() {
        Toast.makeText(this, "Please enter a message text", Toast.LENGTH_SHORT).show()
    }

    override fun messageSent() {
        edittext_chat.text.clear()
        recyclerview_chat.scrollToPosition(adapter.itemCount - 1)
    }

    override fun messageAdded(groupAdapter: GroupAdapter<ViewHolder>) {
        adapter = groupAdapter
        recyclerview_chat.scrollToPosition(adapter.itemCount - 1)
    }

}