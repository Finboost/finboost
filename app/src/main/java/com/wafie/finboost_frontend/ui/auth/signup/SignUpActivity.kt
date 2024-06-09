package com.wafie.finboost_frontend.ui.auth.signup

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.ActivitySignUpBinding
import com.wafie.finboost_frontend.ui.auth.signup.viewmodel.SignUpViewModel
import com.wafie.finboost_frontend.ui.auth.signup.viewmodel.SignUpViewModelFactory
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
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genders)
        binding.genderAutocomplete.setAdapter(genderAdapter)

        roleViewModel.roles.observe(this, Observer { roles ->
            val roleNames = roles.map { it.name ?: "Unknown" }
            val roleId = roles.map { it.id?: "" }
            val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, roleNames)
            binding.roleAutocomplete.setAdapter(adapter)
            binding.roleAutocomplete.setOnItemClickListener { _, _, position, id ->
                selectedRoleId = roleId[position]
            }
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
                Log.d(
                    "SignUpActivity",
                    "Data: $fullName, $email, $password, $phoneNumber, $gender, $selectedRoleId, $age"
                )

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
                            Toast.makeText(this, "Sign-up berhasil", Toast.LENGTH_SHORT).show()

                        } else if(it.status == "fail") {
                            Toast.makeText(this, "Sign-up gagal: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}