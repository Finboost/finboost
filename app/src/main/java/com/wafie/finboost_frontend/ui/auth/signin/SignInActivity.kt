package com.wafie.finboost_frontend.ui.auth.signin

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import com.wafie.finboost_frontend.MainActivity
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.ActivitySignInBinding
import com.wafie.finboost_frontend.databinding.CustomErrorDialogBinding
import com.wafie.finboost_frontend.databinding.CustomSuccessDialogBinding
import com.wafie.finboost_frontend.ui.auth.signin.viewmodel.SignInViewModel
import com.wafie.finboost_frontend.ui.auth.signin.viewmodel.SignInViewModelFactory
import com.wafie.finboost_frontend.ui.auth.signup.SignUpActivity

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

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
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
                    showSuccessDialog("Sign In Berhasil!", "Selamat! Anda berhasil masuk")

                    binding.progressBar.visibility = View.GONE
                } else {
                    showErrorDialog("Oops! Error", "Email atau password Anda salah, silahkan periksa kembali")
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun showSuccessDialog(title: String, message: String) {
        val dialogBinding = CustomSuccessDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.tvDialogTitle.text = title
        dialogBinding.tvDialogMessage.text = message

        dialogBinding.btnOk.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels * 0.9).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    private fun showErrorDialog(title: String, message: String) {
        val dialogBinding = CustomErrorDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.tvDialogTitle.text = title
        dialogBinding.tvDialogMessage.text = message

        dialogBinding.btnOk.setOnClickListener {
            finish()
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels * 0.9).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.btnSignInGoogle.visibility = if (isLoading) View.VISIBLE else  View.GONE
        binding.btnSignIn.visibility = if (isLoading) View.VISIBLE else  View.GONE
        binding.tvSignUp.visibility = if (isLoading) View.VISIBLE else  View.GONE
        binding.tvOr.visibility = if (isLoading) View.VISIBLE else  View.GONE
    }

}