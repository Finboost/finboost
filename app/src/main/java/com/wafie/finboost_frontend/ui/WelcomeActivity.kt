package com.wafie.finboost_frontend.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wafie.finboost_frontend.MainActivity
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.ui.auth.signin.SignInActivity
import com.wafie.finboost_frontend.ui.auth.signup.SignUpActivity
import com.wafie.finboost_frontend.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authAction()
    }

    private fun authAction() {
        binding.btnSignin.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}