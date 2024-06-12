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
import com.wafie.finboost_frontend.ui.chat.expert.adapter.ExpertListAdapter
import com.wafie.finboost_frontend.ui.home.adapter.ExpertAdapter
import com.wafie.finboost_frontend.ui.home.viewmodel.ExpertViewModel
import com.wafie.finboost_frontend.ui.home.viewmodel.ExpertViewModelFactory
import com.wafie.finboost_frontend.utils.Utils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ExpertChat : AppCompatActivity() {
    private lateinit var binding: ActivityExpertChatBinding
    private lateinit var viewModel: ExpertViewModel
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpertChatBinding.inflate(layoutInflater)

        setContentView(binding.root)

        userPreference = UserPreference(dataStore)
        getExpertList()
        onBackPress()
    }

    private fun getExpertList() {
        val token = runBlocking { userPreference.getSession().first().accessToken }
        val decodedJwt = Utils.jwtDecoder(token)
        val userRole = decodedJwt?.getString("role") ?: "User"

        viewModel = ViewModelProvider(this, ExpertViewModelFactory(userPreference))[ExpertViewModel::class.java]

        val expertAdapter = ExpertListAdapter(decodedJwt?.getString("id") ?: "user id not found")
        binding.rvExpertList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = expertAdapter
        }

        val roleToFetch = if (userRole == "Expert") "User" else "Expert"
        viewModel.getListUser(roleToFetch)

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