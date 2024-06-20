package com.wafie.finboost_frontend.ui.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.FragmentProfileBinding
import com.wafie.finboost_frontend.ui.WelcomeActivity
import com.wafie.finboost_frontend.ui.auth.signin.SignInActivity
import com.wafie.finboost_frontend.ui.profile.personalData.PersonalDataActivity
import com.wafie.finboost_frontend.ui.profile.personalData.viewmodel.PersonalDataViewModel
import com.wafie.finboost_frontend.ui.profile.personalData.viewmodel.PersonalDataViewModelFactory
import com.wafie.finboost_frontend.ui.profile.privacy.ProfilePrivacy
import com.wafie.finboost_frontend.ui.profile.settings.SettingsActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.wafie.finboost_frontend.utils.Utils.rupiahFormatter
import java.io.File

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference
    private lateinit var viewModel: PersonalDataViewModel
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreference = UserPreference(requireContext().dataStore)

        lifecycleScope.launch {
            userId = userPreference.getSession().first().id
            val userName = userPreference.getSession().first().fullName

            viewModel = ViewModelProvider(this@ProfileFragment,
                PersonalDataViewModelFactory(userPreference))[PersonalDataViewModel::class.java]

            viewModel.getUserProfileById(userId)

            viewModel.userProfileById.observe(viewLifecycleOwner, Observer { userProfile ->
                if (userProfile != null) {
                    with(binding) {
                        Glide.with(this@ProfileFragment)
                            .load(userProfile.avatar)
                            .into(ivUserProfile)
                        tvUserName.text = userName
                        tvUserIncome.text = rupiahFormatter(userProfile.totalSaving)
                        tvUserExpanse.text = rupiahFormatter(userProfile.totalDebt)
                    }
                }
            })
        }

        binding.ivUserProfile.setOnClickListener {
            if(checkAndRequestPermissions()) {
                openGallery()
            }
        }

        logout()

        //change language
        binding.tvBahasa.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        //navigate to 'Data Pribadi'
        binding.clUser.setOnClickListener {
            val intent = Intent(requireContext(), PersonalDataActivity::class.java)
            startActivity(intent)
        }

        //navigate to 'Privacy'
        binding.clPrivacy.setOnClickListener {
            val intent = Intent(requireContext(), ProfilePrivacy::class.java)
            startActivity(intent)
        }

        //navigate to 'Settings'
        binding.clSetting.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                return false
            }
        }
        return true
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri: Uri = data.data!!
            val filePath = getRealPathFromURI(selectedImageUri)
            if (filePath != null) {
                val file = File(filePath)
                val mimeType = requireActivity().contentResolver.getType(selectedImageUri)
                if (mimeType == "image/jpeg" || mimeType == "image/png") {
                    updateUserPhoto(file)
                } else {
                    Log.e("ProfileFragment", "Invalid file type. Only JPG, PNG are allowed!")
                }
            }
        }
    }


    private fun getRealPathFromURI(uri: Uri): String? {
        var path: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                path = it.getString(columnIndex)
            }
        }
        return path
    }

    private fun updateUserPhoto(file: File) {
        viewModel.updateUserPhoto(
            userId,
            file,
            onComplete = {
                // Handle success, update UI or show a message
                viewModel.getUserProfileById(userId)
            },
            onError = { errorMessage ->
                // Handle error, show a message
                Log.e("ProfileFragment", "Error updating photo: $errorMessage")
            }
        )
    }

    private fun navigateToWelcome() {
        val intent = Intent(requireContext(), SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun logout() {
        binding.tvLogout.setOnClickListener {
            lifecycleScope.launch {
                userPreference.logout()
                navigateToWelcome()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Log.e("ProfileFragment", "Permission denied")
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val STORAGE_PERMISSION_CODE = 101
    }
}
