package com.wafie.finboost_frontend.ui.chat.expert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.databinding.ActivityChatRoomBinding

class ChatRoom : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}