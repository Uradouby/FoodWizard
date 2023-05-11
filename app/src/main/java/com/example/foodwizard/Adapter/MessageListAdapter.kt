package com.example.foodwizard.Fragments

import android.R
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.databinding.ListItemSpeechReceivedBinding

class MessageAdapter(private val messages: List<String>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemSpeechReceivedBinding.inflate(layoutInflater, parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount() = messages.size

    inner class MessageViewHolder(private val binding: ListItemSpeechReceivedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: String) {
            with(binding.messageTextView) {
                text = message
                if (adapterPosition % 2 == 0) {
                    // User message
                    setTextColor(Color.BLACK)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                } else {
                    // System response
                    setTextColor(Color.BLUE)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                }
            }
        }
    }
}





