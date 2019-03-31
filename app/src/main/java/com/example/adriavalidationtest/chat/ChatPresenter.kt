package com.example.adriavalidationtest.chat

import android.content.ContentValues.TAG
import android.util.Log
import com.example.adriavalidationtest.models.Message
import com.example.adriavalidationtest.models.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*

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