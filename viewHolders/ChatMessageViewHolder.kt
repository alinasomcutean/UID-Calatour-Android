package com.example.calatour.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calatour.R
import com.example.calatour.model.ChatMessage
import kotlinx.android.synthetic.main.chat_message_design_1.view.*

class ChatMessageViewHolder(itemView: View, design: Int) :  RecyclerView.ViewHolder(itemView){

    private lateinit var authorTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var timestampTextView: TextView
    private lateinit var starImageView: ImageView

    //Define initialization function
    init {
        if(design == R.layout.chat_message_design_1) {
            authorTextView = itemView.findViewById(R.id.authorTextView)
            contentTextView = itemView.findViewById(R.id.contentTextView)
            timestampTextView = itemView.findViewById(R.id.timestampTextView)
            starImageView = itemView.findViewById(R.id.starImageView)
        } else {
            authorTextView = itemView.findViewById(R.id.author2TextView)
            contentTextView = itemView.findViewById(R.id.content2TextView)
            timestampTextView = itemView.findViewById(R.id.timestamp2TextView)
            starImageView = itemView.findViewById(R.id.star2ImageView)
        }
    }

    fun bindData(data: ChatMessage) {
        authorTextView.text = data.author;
        contentTextView.text = data.content
        timestampTextView.text = data.timestamp

        if(data.important) {
            starImageView.visibility = View.VISIBLE
        } else {
            starImageView.visibility = View.GONE
        }
    }
}