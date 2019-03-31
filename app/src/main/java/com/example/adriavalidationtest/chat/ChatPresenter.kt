package com.example.adriavalidationtest.chat

import android.content.ContentValues.TAG
import android.util.Log
import com.example.adriavalidationtest.friends.FriendsActivity
import com.example.adriavalidationtest.models.Message
import com.example.adriavalidationtest.models.User
import com.example.adriavalidationtest.views.ChatDestinationItem
import com.example.adriavalidationtest.views.ChatSourceItem
import com.example.adriavalidationtest.views.UserItem
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import java.util.Date

class ChatPresenter : ChatContract.Presenter {

    var chatView: ChatContract.View?
    var chatActivity: ChatActivity?
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    constructor(chatView: ChatContract.View, chatActivity: ChatActivity) {
        this.chatView = chatView
        this.chatActivity = chatActivity

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()
    }

    override fun listenForMessages(friend: User) {
        val sourceUid = auth.uid
        val destinationUid = friend?.uid

        val ref = db.collection("user-messages/$sourceUid/roomMessages")
        ref.addSnapshotListener(EventListener<QuerySnapshot> { snapshots, e ->
            if (e != null) {
                Log.w(TAG, "listen:error", e)
                return@EventListener
            }

            val adapter = GroupAdapter<ViewHolder>()

            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        Log.d(TAG, "New message: ${dc.document.data}")

                        val message = dc.document.toObject(Message::class.java)

                        message?.let {
                            if (message.source == auth.uid) {
                                val currentUser = FriendsActivity.currentUser
                                adapter.add(ChatSourceItem(message.text, currentUser!!))
                            } else {
                                adapter.add(ChatDestinationItem(message.text, chatActivity!!.friend!!))
                            }
                        }


                        chatView?.messageAdded(adapter)
                    }
                    DocumentChange.Type.MODIFIED -> Log.d(TAG, "Modified city: ${dc.document.data}")
                    DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed city: ${dc.document.data}")
                }
            }
        })
    }

    override fun sendMessage(text: String) {
        if (text.isEmpty()) {
            chatView?.emptyField()
        } else {
            val sourceUid = auth.uid
            val user = chatActivity!!.intent.getParcelableExtra<User>(User.USER_KEY)
            val destinationUid = user.uid

            sourceUid?.let {
                val sourceRef = db.collection("user-messages").document(it)
                val destinationRef = db.collection("user-messages").document(destinationUid)

                // Message object to store
                val message = Message(sourceRef.id, text, sourceUid, destinationUid, Timestamp(Date()))

                // Dummy data
                val data = HashMap<String, Any>()
                data["uid"] = it

                // Store for sender
                sourceRef.set(data, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot successfully written!")

                        val newSourceRef = sourceRef.collection("roomMessages").document(destinationUid)
                        addMessageToReference(newSourceRef, message)

                        /*

                        */
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }

                // Store for receiver
                destinationRef.set(data, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot successfully written!")

                        val newDestinationRef = destinationRef.collection("roomMessages").document(sourceUid!!)
                        addMessageToReference(newDestinationRef, message)
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            }
        }
    }

    private fun addMessageToReference(ref: DocumentReference, message: Message) {
        ref.set(message)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")

            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

}