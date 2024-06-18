package com.wafie.finboost_frontend.ui.profile.personalData

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wafie.finboost_frontend.MainActivity
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.ActivityEditPersonalDataBinding
import com.wafie.finboost_frontend.databinding.CustomErrorDialogBinding
import com.wafie.finboost_frontend.databinding.CustomSuccessDialogBinding
import com.wafie.finboost_frontend.ui.auth.signin.SignInActivity
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
                            showErrorDialog("Update Data Berhasil", "Data diri anda berhasil diperbarui")
                        } else {
                            showErrorDialog("Oops! Error", "Terjadi kesalahan, pastikan data yang anda perbarui sudah benar")
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
            val adapter = ArrayAdapter(this, R.layout.custom_dropdown_item, educationName)
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
            val adapter = ArrayAdapter(this, R.layout.custom_dropdown_item, worksName)
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
        val maritalStatusAdapter = ArrayAdapter(this, R.layout.custom_dropdown_item, maritalStatus)
        binding.autoCompleteStatus.setAdapter(maritalStatusAdapter)
    }

    private fun getInvestmentType() {
        val typeofInvestment = TypeofInvestments.entries.map { it.displayName }
        val investmentAdapter = ArrayAdapter(this, R.layout.custom_dropdown_item, typeofInvestment)
        binding.autoCompleteUserInvestment.setAdapter(investmentAdapter)
    }

    private fun getInsuranceType() {
        val typeofInsurances = TypeofInsurances.entries.map { it.displayName }
        val investmentAdapter = ArrayAdapter(this, R.layout.custom_dropdown_item, typeofInsurances)
        binding.autoCompleteUserInsurance.setAdapter(investmentAdapter)
    }

    private fun showSuccessDialog(title: String, message: String) {
        val dialogBinding = CustomSuccessDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.tvDialogTitle.text = title
        dialogBinding.tvDialogMessage.text = message

        dialogBinding.btnOk.setOnClickListener {
            val intent = Intent(this, PersonalDataActivity::class.java)
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

    override fun onBackPressed() {
        super.onBackPressed()
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
    companion object {
        private const val  TAG = "EditPersonalDataActivity"
    }

}