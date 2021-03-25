package com.example.calatour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calatour.adapters.ChatAdapter
import com.example.calatour.model.ChatMessage
import com.example.calatour.model.UserInfoSingleton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    //List where we will hold the info
    private val dataSource = ArrayList<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter

    private lateinit var authorTextView: TextView
    private lateinit var contentEditText: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatAdapter = ChatAdapter(this, dataSource)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val chatRecyclerView = findViewById<RecyclerView>(R.id.chatMessagesRecyclerView)
        chatRecyclerView.layoutManager = layoutManager
        chatRecyclerView.adapter = chatAdapter

        authorTextView = findViewById(R.id.addMessageAuthorTextView)
        contentEditText = findViewById(R.id.addMessageContentEditText)
        sendButton = findViewById(R.id.addMessageSendButton)

        sendButton.setOnClickListener(this)

        authorTextView.text = UserInfoSingleton.displayName
    }

    override fun onClick(view: View?) {
        dataSource.add(
            ChatMessage(
                authorTextView.text.toString(),
                contentEditText.text.toString(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now())
            )
        )

        chatAdapter.notifyItemInserted(0)
        contentEditText.text.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat_options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.chatOptionsMenuDesign1) {
            chatAdapter.setDesign(1)
        } else if(item.itemId == R.id.chatOptionsMenuDesign2) {
            chatAdapter.setDesign(2)
        } else if(item.itemId == R.id.chatOptionsMenuBothDesigns){
            chatAdapter.setDesign(3)
        }
        return super.onOptionsItemSelected(item)
    }
}
