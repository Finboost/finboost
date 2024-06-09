package com.wafie.finboost_frontend.ui.chat.finAi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.ActivityFinAiChatBinding
import com.wafie.finboost_frontend.ui.chat.finAi.adapter.FinAiAdapter
import com.wafie.finboost_frontend.ui.chat.finAi.viewmodel.FinAiViewModel
import com.wafie.finboost_frontend.ui.chat.finAi.viewmodel.FinAiViewModelFactory

class FinAiChat : AppCompatActivity() {
    private lateinit var binding: ActivityFinAiChatBinding
//    private val viewModel: FinAiViewModel by viewModels()
    private val viewModel: FinAiViewModel by viewModels {
        FinAiViewModelFactory(UserPreference.getInstance(dataStore))
}
    private lateinit var chatAdapter: FinAiAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinAiChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatAdapter = FinAiAdapter()
        binding.rvFinAi.apply {
            layoutManager = LinearLayoutManager(this@FinAiChat)
            adapter = chatAdapter
        }

        binding.btnSend.setOnClickListener {
            val message = binding.edtChatAi.text.toString()
            if (message.isNotBlank()) {
                viewModel.sendQuestion(message)
                binding.edtChatAi.text!!.clear()
            }
        }

        viewModel.question.observe(this, Observer { question ->
            chatAdapter.submitList(question)
        })

        viewModel.error.observe(this, Observer { error ->
            // Handle error
        })
    }
}