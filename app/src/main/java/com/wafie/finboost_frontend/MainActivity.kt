package com.wafie.finboost_frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wafie.finboost_frontend.databinding.ActivityMainBinding
import com.wafie.finboost_frontend.ui.chat.expert.ExpertChat
import com.wafie.finboost_frontend.ui.chat.finAi.FinAiChat
import com.wafie.finboost_frontend.ui.home.HomeFragment
import com.wafie.finboost_frontend.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())
        navigateMenu()

    }

    //navigate to selected menu
    private fun navigateMenu() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.message -> {
                    startActivity(Intent(this, ExpertChat::class.java))
                    true
                }
                R.id.finAi -> {
                    startActivity(Intent(this, FinAiChat::class.java))
                    true
                }
                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    //replace fragment after moved into different fragment
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}