package com.example.adriavalidationtest.friends

import android.content.ContentValues.TAG
import android.util.Log
import com.example.adriavalidationtest.models.User
import com.example.adriavalidationtest.models.UserItem
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class FriendsPresenter : FriendsContract.Presenter {

    var friendsView: FriendsContract.View?
    private lateinit var db: FirebaseFirestore

    constructor(friendsView: FriendsContract.View) {
        this.friendsView = friendsView

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()
    }

    override fun fetchUsers() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val adapter = GroupAdapter<ViewHolder>()

                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")

                    val user = document.toObject(User::class.java)
                    adapter.add(UserItem(user))
                }

                adapter.setOnItemClickListener { item, view ->
                    val userItem = item as UserItem
                    friendsView?.adapterClickListener(userItem, view)
                }

                friendsView?.usersListenerSuccess(adapter)
            }
            .addOnFailureListener { exception ->
                friendsView?.usersListenerFailed(exception)
            }
    }

}