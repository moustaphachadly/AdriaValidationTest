package com.example.adriavalidationtest.views

import com.example.adriavalidationtest.R
import com.example.adriavalidationtest.models.User
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_destination_cell.view.*

class ChatDestinationItem(val text: String, val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_destination_cell.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_destination_cell
    }
}