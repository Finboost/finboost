package com.wafie.finboost_frontend.ui.auth.signup


import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.wafie.finboost_frontend.MainActivity
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.databinding.ActivitySignUpBinding
import com.wafie.finboost_frontend.databinding.CustomErrorDialogBinding
import com.wafie.finboost_frontend.databinding.CustomSuccessDialogBinding
import com.wafie.finboost_frontend.ui.auth.signin.SignInActivity
import com.wafie.finboost_frontend.ui.auth.signup.viewmodel.SignUpViewModel
import com.wafie.finboost_frontend.ui.viewmodel.RolesViewModel
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val roleViewModel: RolesViewModel by viewModels()
    private val  signUpViewModel: SignUpViewModel by viewModels()
    private var selectedRoleId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genders = listOf("Laki_laki", "Perempuan")
        val genderAdapter = ArrayAdapter(this, R.layout.custom_dropdown_item, genders)
        binding.genderAutocomplete.setAdapter(genderAdapter)

        roleViewModel.roles.observe(this, Observer { roles ->
            val roleNames = roles.map { it.name ?: "Unknown" }
            val roleId = roles.map { it.id?: "" }
            val adapter = ArrayAdapter(this, R.layout.custom_dropdown_item, roleNames)
            binding.roleAutocomplete.setAdapter(adapter)
            binding.roleAutocomplete.setOnItemClickListener { _, _, position, id ->
                selectedRoleId = roleId[position]
            }
        })

        signUpViewModel.isLoading.observe(this, Observer {
            showLoading(it)
        })

        lifecycleScope.launch {
            roleViewModel.fetchRoles()
        }

        register()
    }
    //register
    private fun register() {
        binding.btnSignup.setOnClickListener {
            val fullName = binding.edtFullName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val phoneNumber = binding.edtPhone.text.toString()
            val gender = binding.genderAutocomplete.text.toString()
            val age = binding.edtAge.text.toString().toIntOrNull()

            if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() &&
                phoneNumber.isNotEmpty() && gender.isNotEmpty() && age != null && selectedRoleId != null
            ) {
                signUpViewModel.signUp(
                    fullName,
                    email,
                    password,
                    phoneNumber,
                    gender,
                    selectedRoleId!!,
                    age
                )

                signUpViewModel.signUpResult.observe(this, Observer { signUpResponse ->
                    signUpResponse?.let {
                        if (it.status == "success") {
                            showSuccessDialog("Sign-Up Berhasil", "Selamat ${it.fullName}! akun anda berhasil dibuat")
                            clearEditText()
                        } else if(it.status == "fail") {
                            showErrorDialog("Oops! Sign-up gagal", "Sayang sekali, akun dengan email ${it.email} sudah ada")
                        }
                    }
                })
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.btnSignup.visibility = if (isLoading) View.VISIBLE else  View.GONE
    }
    private fun clearEditText(){
        binding.edtAge.setText("")
        binding.edtEmail.setText("")
        binding.edtPassword.setText("")
        binding.edtFullName.setText("")
        binding.edtPhone.setText("")
        binding.roleAutocomplete.setText("")
    }

    private fun showSuccessDialog(title: String, message: String) {
        val dialogBinding = CustomSuccessDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.tvDialogTitle.text = title
        dialogBinding.tvDialogMessage.text = message

        dialogBinding.btnOk.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
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
            val intent = Intent(this, SignUpActivity::class.java)
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

}