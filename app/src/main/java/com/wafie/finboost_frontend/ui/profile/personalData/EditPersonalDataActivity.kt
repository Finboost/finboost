package com.wafie.finboost_frontend.ui.profile.personalData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.ActivityEditPersonalDataBinding
import com.wafie.finboost_frontend.ui.profile.personalData.viewmodel.PersonalDataViewModel
import com.wafie.finboost_frontend.ui.profile.personalData.viewmodel.PersonalDataViewModelFactory
import com.wafie.finboost_frontend.ui.viewmodel.EducationsViewModel
import com.wafie.finboost_frontend.ui.viewmodel.WorksViewModel
import kotlinx.coroutines.launch

enum class MaritalStatus (val displayName: String){
    MENIKAH("Menikah"),
    LAJANG("Lajang"),
    CERAI("Cerai")
}

enum class TypeofInvestments(val displayName: String) {
    SAHAM("Saham"),
    REKSADANA("Reksadana"),
    OBLIGASI("Obligasi"),
    EMAS("Emas"),
    CRYPTOCURRENCY("Crypto")
}

enum class TypeofInsurances(val displayName: String) {
    SAHAM("Saham"),
    REKSADANA("Reksadana"),
    OBLIGASI("Obligasi"),
    EMAS("Emas"),
    CRYPTOCURRENCY("Crypto")
}

class EditPersonalDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditPersonalDataBinding
    private lateinit var worksViewModel: WorksViewModel
    private lateinit var eduViewModel: EducationsViewModel

    private var selectedEduId: String? = null
    private var selectedWorkId: String? = null

    private  val viewModel: PersonalDataViewModel by viewModels{
        PersonalDataViewModelFactory(UserPreference.getInstance(applicationContext.dataStore))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initial the viewModel
        eduViewModel = ViewModelProvider(this)[EducationsViewModel::class.java]
        worksViewModel = ViewModelProvider(this)[WorksViewModel::class.java]

        val userStatus = intent.getStringExtra("userStatus")
        val userWork = intent.getStringExtra("userWork")
        val userEducation = intent.getStringExtra("userEducation")
        val userTypeInvestment = intent.getStringExtra("userTypeInvestment")
        val userTypeInsurance = intent.getStringExtra("userTypeInsurance")
        val userIncome = intent.getStringExtra("userIncome")

        binding.autoCompleteStatus.setText(userStatus)
        binding.autoCompleteWork.setText(userWork)
        binding.autoCompleteEducations.setText(userEducation)
        binding.autoCompleteUserInvestment.setText(userTypeInvestment)
        binding.autoCompleteUserInsurance.setText(userTypeInsurance)
        binding.edtUserIncome.setText(userIncome)

        getInvestmentType()
        getInsuranceType()
        getMaritalStatus()
        getAllEducations()
        getAllWorks()

        editProfile()

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun editProfile() {
        val userId = intent.getStringExtra("userId")
        binding.btnSaveChanges.setOnClickListener {
            if (userId != null) {
                viewModel.updateUserProfileById(
                    userId,
                    binding.autoCompleteStatus.text.toString(),
                    selectedEduId!!,
                    selectedWorkId!!,
                    binding.autoCompleteUserInvestment.text.toString(),
                    binding.autoCompleteUserInsurance.text.toString(),
                    binding.edtUserIncome.text.toString(),
                    onComplete = { isSuccess ->
                        if (isSuccess) {
                            Log.d(TAG, "Data berhasil diperbarui")
                            startActivity(Intent(this, PersonalDataActivity::class.java))
                        } else {
                            // Handle update failure, e.g., show an error message
                        }
                    },
                    onError = { errorMessage ->
                        // Handle error, e.g., show an error message
                    }
                )
            }
        }
    }

    private fun getAllEducations() {
        eduViewModel.edu.observe(this, Observer {educations ->
            val educationName = educations.map { it.name ?: "Unknown" }
            val educationId = educations.map { it.id?: "" }
            val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, educationName)
            binding.autoCompleteEducations.setAdapter(adapter)
            binding.autoCompleteEducations.setOnItemClickListener { _, _, position, id ->
                selectedEduId = educationId[position]
            }
        })

        lifecycleScope.launch {
            eduViewModel.fetchEducation()
        }
    }

    private fun getAllWorks() {
        worksViewModel.works.observe(this, Observer {  works ->
            val workId = works.map { it.id ?: "" }
            val worksName = works.map { it.name ?: "Unknown" }
            val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, worksName)
            binding.autoCompleteWork.setAdapter(adapter)
            binding.autoCompleteWork.setOnItemClickListener { _, _, position, id ->
                selectedWorkId = workId [position]
            }
        })
        lifecycleScope.launch {
            worksViewModel.fetchWorks()
        }
    }

    private fun getMaritalStatus() {
        val maritalStatus = MaritalStatus.entries.map { it.displayName }
        val maritalStatusAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, maritalStatus)
        binding.autoCompleteStatus.setAdapter(maritalStatusAdapter)
    }

    private fun getInvestmentType() {
        val typeofInvestment = TypeofInvestments.entries.map { it.displayName }
        val investmentAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, typeofInvestment)
        binding.autoCompleteUserInvestment.setAdapter(investmentAdapter)
    }

    private fun getInsuranceType() {
        val typeofInsurances = TypeofInsurances.entries.map { it.displayName }
        val investmentAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, typeofInsurances)
        binding.autoCompleteUserInsurance.setAdapter(investmentAdapter)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    companion object {
        private const val  TAG = "EditPersonalDataActivity"
    }

}