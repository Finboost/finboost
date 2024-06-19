package com.wafie.finboost_frontend.ui.chat.finAi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.ActivityFinAiChatBinding
import com.wafie.finboost_frontend.ui.chat.finAi.adapter.ChatItem
import com.wafie.finboost_frontend.ui.chat.finAi.adapter.FinAiAdapter
import com.wafie.finboost_frontend.ui.chat.finAi.adapter.FinAiQuestionAdapter
import com.wafie.finboost_frontend.ui.chat.finAi.viewmodel.FinAiViewModel
import com.wafie.finboost_frontend.ui.chat.finAi.viewmodel.FinAiViewModelFactory

class FinAiChat : AppCompatActivity() {
    private lateinit var binding: ActivityFinAiChatBinding
    private val viewModel: FinAiViewModel by viewModels {
        FinAiViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private lateinit var chatAdapter: FinAiAdapter
    private lateinit var aiSuggestedQuetionAdapter: FinAiQuestionAdapter
    private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinAiChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatAdapter = FinAiAdapter()
        binding.rvFinAi.apply {
            layoutManager = LinearLayoutManager(this@FinAiChat)
            adapter = chatAdapter
        }

        aiSuggestedQuetionAdapter = FinAiQuestionAdapter(listOf()) { question ->
            viewModel.sendQuestion(question)

            val currentList = chatAdapter.currentList.toMutableList()
            currentList.add(ChatItem.UserQuestion(question))
            currentList.add(ChatItem.Shimmer) // Tambahkan item shimmer
            chatAdapter.submitList(currentList)

            // Clear input text after send question
            binding.edtChatAi.text!!.clear()
        }
        binding.rvAiSuggestion.apply {
            layoutManager = LinearLayoutManager(this@FinAiChat, LinearLayoutManager.HORIZONTAL, false)
            adapter = aiSuggestedQuetionAdapter
        }


        sendChat()

        viewModel.aiAnswer.observe(this, Observer { aiAnswer ->
            val currentList = chatAdapter.currentList.filterNot { it is ChatItem.Shimmer }.toMutableList()
            currentList.add(ChatItem.AiAnswer(aiAnswer))
            chatAdapter.submitList(currentList)
        })

        viewModel.aiSuggestion.observe(this, Observer { suggestions ->
            aiSuggestedQuetionAdapter.updateSuggestions(suggestions)
        })

        viewModel.aiSuggestion(5, "-")


        onBackPress()
    }

    private fun sendChat() {
        binding.btnSend.setOnClickListener {
            val message = binding.edtChatAi.text.toString().trim()
            if (message.isNotBlank()) {
                viewModel.sendQuestion(message)

                val currentList = chatAdapter.currentList.toMutableList()
                currentList.add(ChatItem.UserQuestion(message))
                currentList.add(ChatItem.Shimmer)
                chatAdapter.submitList(currentList)

                binding.edtChatAi.text!!.clear()
            }
        }
    }
    private fun onBackPress() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        private const val MESSAGES = "Chat With Fin Ai"
        private const val CHAT_ROOM_ID = "CHAT_ROOM_ID"
    }

}
