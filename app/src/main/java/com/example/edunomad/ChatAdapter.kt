package com.example.edunomad
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.edunomad.ChatMessage
import com.example.edunomad.R
import java.text.SimpleDateFormat
import java.util.*


class ChatAdapter(private val chatList: List<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_USER = 0
        const val VIEW_TYPE_BOT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].isUser) VIEW_TYPE_USER else VIEW_TYPE_BOT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_USER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_message, parent, false)
            UserViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bot_message, parent, false)
            BotViewHolder(view)
        }
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = chatList[position]
        val formattedTime = formatTimestamp(chat.timestamp)

        if (holder is UserViewHolder) {
            holder.message.text = chat.message
            holder.time.text = formattedTime
        } else if (holder is BotViewHolder) {
            holder.message.text = chat.message
            holder.time.text = formattedTime
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    // User message view holder
    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.userMessage)
        val time: TextView = view.findViewById(R.id.userTime)
    }

    // Bot message view holder
    class BotViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.botMessage)
        val time: TextView = view.findViewById(R.id.botTime)
    }
}
