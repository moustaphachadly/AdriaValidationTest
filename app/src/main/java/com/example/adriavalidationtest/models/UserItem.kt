package com.example.adriavalidationtest.models

import android.support.v7.content.res.AppCompatResources
import com.example.adriavalidationtest.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_item.view.*

data class UserItem(val user: User) : Item<ViewHolder>() {

    override fun getLayout() = R.layout.user_item

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_user_item.text = user.username
    }
}