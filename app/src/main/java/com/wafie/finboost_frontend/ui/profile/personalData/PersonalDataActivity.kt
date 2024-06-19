package com.wafie.finboost_frontend.ui.profile.personalData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.ActivityPersonalDataBinding
import com.wafie.finboost_frontend.ui.profile.personalData.viewmodel.PersonalDataViewModel
import com.wafie.finboost_frontend.ui.profile.personalData.viewmodel.PersonalDataViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.wafie.finboost_frontend.utils.Utils.rupiahFormatter


class PersonalDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalDataBinding
    private  val viewModel: PersonalDataViewModel by viewModels{
        PersonalDataViewModelFactory(UserPreference.getInstance(applicationContext.dataStore))
    }

    private lateinit var userPreference: UserPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(applicationContext.dataStore)
        getUserProfile()

        binding.btnEditData.setOnClickListener {
            val intent = Intent(this, EditPersonalDataActivity::class.java).apply {
                putExtra("userId", binding.tvUserId.text.toString())
                putExtra("userStatus", binding.tvUserStatus.text.toString())
                putExtra("userWork", binding.tvUserWork.text.toString())
                putExtra("userEducation", binding.tvUserEducation.text.toString())
                putExtra("userTypeInvestment", binding.tvUserTypeInvestment.text.toString())
                putExtra("userTypeInsurance", binding.tvUserTypeInsurance.text.toString())
                putExtra("userIncome", binding.tvUserIncome.text.toString())
                putExtra("userAbout", binding.tvUserAbout.text)
            }
            startActivity(intent)
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getUserProfile() {

        val userId = runBlocking { userPreference.getSession().first().id }
        val userFullName = runBlocking { userPreference.getSession().first().fullName }

        viewModel.getUserProfileById(userId)
        userId.let {
            viewModel.userProfileById.observe(this, Observer {userProfile ->
                if (userProfile != null) {
                    with(binding) {
                        tvUserId.text = userId
                        tvUserFullname.text = userFullName
                        tvUserStatus.text = userProfile.maritalStatus
                        tvUserWork.text = userProfile.work?.name
                        tvUserEducation.text = userProfile.education?.name
                        tvUserTypeInvestment.text = userProfile.investment
                        tvUserTypeInsurance.text = userProfile.insurance
                        tvUserIncome.text = rupiahFormatter(userProfile.incomePerMonth)
                        tvUserAbout.text = userProfile.about
                    }
                }
            })
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}