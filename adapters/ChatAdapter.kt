package com.example.calatour.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calatour.R
import com.example.calatour.model.ChatMessage
import com.example.calatour.viewHolders.ChatMessageViewHolder
import javax.sql.DataSource

class ChatAdapter(
    private val context: Context,
    private val dataSource: ArrayList<ChatMessage>
) : RecyclerView.Adapter<ChatMessageViewHolder>() {

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var design: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageViewHolder {
        var rowDesign = inflater.inflate(viewType, parent, false)
        return ChatMessageViewHolder(rowDesign, viewType)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: ChatMessageViewHolder, position: Int) {
        holder.bindData(dataSource.elementAt(position))
    }

    override fun getItemViewType(position: Int): Int {
        if(design == 1) {
            return R.layout.chat_message_design_1
        } else if(design == 2) {
            return R.layout.chat_message_design_2
        } else {
            if(position % 2 == 0) {
                return R.layout.chat_message_design_1
            } else {
                return R.layout.chat_message_design_2
            }
        }
    }

    fun setDesign(design: Int) {
        this.design = design
        notifyDataSetChanged()
    }
}