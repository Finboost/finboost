package com.wafie.finboost_frontend.ui.profile.privacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.databinding.ActivityProfilePrivacyBinding

class ProfilePrivacy : AppCompatActivity() {

    private lateinit var binding: ActivityProfilePrivacyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}