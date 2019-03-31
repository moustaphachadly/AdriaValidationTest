package com.example.adriavalidationtest.friends

import android.content.ContentValues
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.adriavalidationtest.R
import com.example.adriavalidationtest.models.UserItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_friends.*

class FriendsActivity : AppCompatActivity(), FriendsContract.View {

    var friendsPresenter: FriendsPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        // Navigation bar title
        supportActionBar?.title = "Select User"

        // Setup presenter
        if (friendsPresenter == null) {
            friendsPresenter = FriendsPresenter(this)
        }

        // Load users
        friendsPresenter?.fetchUsers()
    }

    override fun adapterClickListener(item: UserItem, view: View) {
        // show chat
    }

    override fun usersListenerSuccess(groupAdapter: GroupAdapter<ViewHolder>) {
        recyclerview_friends.adapter = groupAdapter
    }

    override fun usersListenerFailed(exception: Exception) {
        Log.d(ContentValues.TAG, "Error getting documents: ", exception)
    }

}
