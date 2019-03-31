package com.example.adriavalidationtest.views

import com.example.adriavalidationtest.R
import com.example.adriavalidationtest.models.User
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_destination_cell.view.*
import kotlinx.android.synthetic.main.chat_source_cell.view.*

class ChatSourceItem(val text: String, val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_source_cell.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_source_cell
    }
}
