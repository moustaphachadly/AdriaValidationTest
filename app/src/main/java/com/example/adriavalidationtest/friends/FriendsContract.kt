package com.example.adriavalidationtest.friends

import com.example.adriavalidationtest.models.UserItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class FriendsContract {

    interface View {
        fun adapterClickListener(item: UserItem, view: android.view.View)
        fun usersListenerSuccess(groupAdapter: GroupAdapter<ViewHolder>)
        fun usersListenerFailed(exception: Exception)
    }

    interface Presenter {
        fun fetchUsers()
    }

}