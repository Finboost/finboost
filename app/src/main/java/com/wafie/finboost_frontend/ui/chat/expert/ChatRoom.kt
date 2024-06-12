package com.wafie.finboost_frontend.ui.chat.expert

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.wafie.finboost_frontend.data.model.MessageModel
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.ActivityChatRoomBinding
import com.wafie.finboost_frontend.ui.chat.expert.adapter.FirebaseMessageAdapter
import com.wafie.finboost_frontend.ui.chat.expert.adapter.ChatItem
import com.wafie.finboost_frontend.utils.Utils.jwtDecoder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Date

class ChatRoom : AppCompatActivity() {

    private lateinit var binding: ActivityChatRoomBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var userPreference: UserPreference

    private lateinit var chatRoomId: String
    private lateinit var adapter: FirebaseMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(dataStore)

        chatRoomId = intent.getStringExtra(CHAT_ROOM_ID) ?: return

        setupRecyclerView()
        setupFirebaseDatabase()
        sendChat()
    }

    private fun setupRecyclerView() {
        adapter = FirebaseMessageAdapter()
        binding.rvChatExpert.layoutManager = LinearLayoutManager(this)
        binding.rvChatExpert.adapter = adapter
    }

    private fun setupFirebaseDatabase() {
        db = FirebaseDatabase.getInstance()
        val messageRef = db.reference.child(MESSAGES).child(chatRoomId)

        messageRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(MessageModel::class.java)
                message?.let {
                    val chatItem = if (it.id == getUserId()) {
                        ChatItem.UserQuestion(it.message)
                    } else {
                        ChatItem.AiAnswer(it.message)
                    }
                    adapter.submitList(adapter.currentList + chatItem)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatRoom, "Failed to load messages: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendChat() {
        val messageRef = db.reference.child(MESSAGES).child(chatRoomId)

        val token = runBlocking { userPreference.getSession().first().accessToken }
        val decodedJwt = jwtDecoder(token)

        binding.btnSend.setOnClickListener {
            val message = MessageModel(
                decodedJwt?.getString("id") ?: "",
                decodedJwt?.getString("fullName") ?: "",
                binding.edtExpertChat.text.toString(),
                Date().time
            )
            messageRef.push().setValue(message) { error, _ ->
                if (error != null) {
                    Toast.makeText(this, "Gagal terkirim: ${error.message}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Berhasil Terkirim", Toast.LENGTH_SHORT).show()
                }
                binding.edtExpertChat.text?.clear()
            }
        }
    }

    private fun getUserId(): String {
        val token = runBlocking { userPreference.getSession().first().accessToken }
        val decodedJwt = jwtDecoder(token)
        return decodedJwt?.getString("id") ?: ""
    }

    companion object {
        private const val MESSAGES = "Chat With Expert"
        private const val CHAT_ROOM_ID = "CHAT_ROOM_ID"
    }
}
