package com.wafie.finboost_frontend.ui.chat.expert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.ActivityExpertChatBinding
import com.wafie.finboost_frontend.ui.chat.adapter.ExpertListAdapter
import com.wafie.finboost_frontend.ui.home.adapter.ExpertAdapter
import com.wafie.finboost_frontend.ui.home.viewmodel.ExpertViewModel
import com.wafie.finboost_frontend.ui.home.viewmodel.ExpertViewModelFactory

class ExpertChat : AppCompatActivity() {
    private lateinit var binding: ActivityExpertChatBinding
    private lateinit var viewModel: ExpertViewModel
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpertChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getExpertList()

        onBackPress()
    }

    private fun getExpertList() {
        userPreference = UserPreference.getInstance(dataStore)

        viewModel = ViewModelProvider(this, ExpertViewModelFactory(userPreference))[ExpertViewModel::class.java]

        val expertAdapter = ExpertListAdapter()
        binding.rvExpertList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = expertAdapter
        }
        viewModel.getListExpert()

        viewModel.expertList.observe(this, Observer { expertList ->
            expertList?.let { expertAdapter.submitList(it) }
        })
    }
    private fun onBackPress() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}