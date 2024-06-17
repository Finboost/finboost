package com.wafie.finboost_frontend.ui.auth.signin

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.wafie.finboost_frontend.MainActivity
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.ActivitySignInBinding
import com.wafie.finboost_frontend.ui.auth.signin.viewmodel.SignInViewModel
import com.wafie.finboost_frontend.ui.auth.signin.viewmodel.SignInViewModelFactory

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val signInViewModel: SignInViewModel by viewModels {
        SignInViewModelFactory(UserPreference.getInstance(applicationContext.dataStore))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signIn()

        signInViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun signIn() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInViewModel.signIn(email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }

        }
        signInViewModel.signInResult.observe(this) { response ->
            response?.let {
                if (it.status == "success") {
                    AlertDialog.Builder(this)
                        .setTitle("Login Berhasil")
                        .setMessage("Anda berhasil login.")
                        .setPositiveButton("OK") { _, _ ->
                            startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                            finish()
                        }
                        .show()

                    binding.progressBar.visibility = View.GONE
                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.btnSignInGoogle.visibility = if (isLoading) View.VISIBLE else  View.GONE
        binding.btnSignIn.visibility = if (isLoading) View.VISIBLE else  View.GONE
        binding.tvNoAccount.visibility = if (isLoading) View.VISIBLE else  View.GONE
        binding.tvOr.visibility = if (isLoading) View.VISIBLE else  View.GONE
    }

}