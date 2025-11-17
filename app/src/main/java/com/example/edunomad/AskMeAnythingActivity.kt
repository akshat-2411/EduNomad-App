package com.example.edunomad

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edunomad.R
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class AskMeAnythingActivity : AppCompatActivity() {

    private lateinit var emptyChatHint: TextView // add this at the top with other lateinit vars
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var userInput: EditText
    private lateinit var sendButton: Button
    private lateinit var chatAdapter: ChatAdapter
    private val chatList = mutableListOf<ChatMessage>()

    private val firestore = FirebaseFirestore.getInstance()
    private val GEMINI_API_KEY = "AIzaSyBvUh0kfT770ZPpAojy9nNgo3n9B4RzV-k" // Replace this with a secure method

    private val faqMap = mapOf(
        "how to enroll" to "Go to the Courses section, select your course, and tap the Enroll button.",
        "enroll multiple courses" to "Yes, you can enroll in as many courses as you want.",
        "see my enrolled courses" to "Go to the My Courses section in the bottom navigation bar.",
        "watch course videos" to "Open the course → tap on any topic → you’ll find the video link.",
        "download pdf notes" to "Each topic includes a PDF link next to the video link.",
        "mark topics as completed" to "Yes, there’s a checkbox beside every topic to help you track your progress.",
        "track my course progress" to "In My Courses, completed topics will have a tick mark next to them.",
        "reset my progress" to "This feature is not currently available, but coming soon.",
        "progress not updating" to "Ensure internet connection and that the topic checkbox is marked.",
        "change my email" to "This feature is currently under development.",
        "forgot password" to "Use the Forgot Password option on the login screen.",
        "use app without login" to "Login is required to enroll in courses and save your progress.",
        "video not opening" to "Check your internet connection or try again later.",
        "pdf not loading" to "Ensure you have a PDF viewer installed on your device.",
        "app is crashing" to "Try restarting the app or reinstalling it from the Play Store."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_askme_anything)

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        userInput = findViewById(R.id.userInput)
        sendButton = findViewById(R.id.sendButton)

        emptyChatHint = findViewById(R.id.emptyChatHint)
        checkIfChatIsEmpty() // show hint at launch


        chatAdapter = ChatAdapter(chatList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = chatAdapter

        sendButton.setOnClickListener {
            val question = userInput.text.toString().trim()
            if (question.isNotEmpty()) {
                addMessage(ChatMessage("You", question, isUser = true))
                userInput.setText("")

                val faqAnswer = matchFAQ(question.lowercase())
                if (faqAnswer != null) {
                    addMessage(ChatMessage("Bot", faqAnswer, isUser = false))
                } else {
                    fetchGeminiResponse(question)
                }
            }
        }
    }


    private fun addMessage(message: ChatMessage) {
        chatList.add(message)
        chatAdapter.notifyItemInserted(chatList.size - 1)
        chatRecyclerView.scrollToPosition(chatList.size - 1)
        checkIfChatIsEmpty()
    }


    private fun matchFAQ(input: String): String? {
        return faqMap.entries.find { input.contains(it.key) }?.value
    }

    private fun fetchGeminiResponse(question: String) {
        // Show "Typing..." message
        val typingMessage = ChatMessage("Bot", "Typing...", isUser = false)
        addMessage(typingMessage)
        val typingIndex = chatList.lastIndex

        val url ="https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$GEMINI_API_KEY"

        val json = JSONObject()
        val contents = JSONArray()
        val part = JSONObject().put("text", question)
        val innerObj = JSONObject().put("parts", JSONArray().put(part))
        contents.put(innerObj)
        json.put("contents", contents)

        val body = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    chatList[typingIndex] = ChatMessage("Bot", "Error occurred.", isUser = false)
                    chatAdapter.notifyItemChanged(typingIndex)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    try {
                        val jsonResponse = JSONObject(responseBody)
                        val text = jsonResponse
                            .getJSONArray("candidates")
                            .getJSONObject(0)
                            .getJSONObject("content")
                            .getJSONArray("parts")
                            .getJSONObject(0)
                            .getString("text")

                        runOnUiThread {
                            chatList[typingIndex] = ChatMessage("Bot", text, isUser = false)
                            chatAdapter.notifyItemChanged(typingIndex)
                            saveChatToFirestore(question, text)
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            chatList[typingIndex] = ChatMessage("Bot", "Couldn't parse response.", isUser = false)
                            chatAdapter.notifyItemChanged(typingIndex)
                        }
                    }
                } else {
                    runOnUiThread {
                        chatList[typingIndex] = ChatMessage("Bot", "Failed to get response.", isUser = false)
                        chatAdapter.notifyItemChanged(typingIndex)
                    }
                }
            }
        })
    }


    private fun saveChatToFirestore(userMessage: String, botReply: String) {
        val chatMap = hashMapOf(
            "user" to userMessage,
            "bot" to botReply,
            "timestamp" to System.currentTimeMillis()
        )
        firestore.collection("chats").add(chatMap)
    }
    private fun checkIfChatIsEmpty() {
        if (chatList.isEmpty()) {
            emptyChatHint.visibility = View.VISIBLE
        } else {
            emptyChatHint.visibility = View.GONE
        }
    }


}
