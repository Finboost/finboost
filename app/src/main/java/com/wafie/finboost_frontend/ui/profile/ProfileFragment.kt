package com.wafie.finboost_frontend.ui.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.FragmentProfileBinding
import com.wafie.finboost_frontend.ui.WelcomeActivity
import com.wafie.finboost_frontend.ui.profile.personalData.PersonalDataActivity
import com.wafie.finboost_frontend.ui.profile.personalData.viewmodel.PersonalDataViewModel
import com.wafie.finboost_frontend.ui.profile.personalData.viewmodel.PersonalDataViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.wafie.finboost_frontend.utils.Utils.rupiahFormatter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference
    private lateinit var viewModel: PersonalDataViewModel

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
            val userId = userPreference.getSession().first().id
            val userName = userPreference.getSession().first().fullName

            viewModel = ViewModelProvider(this@ProfileFragment,
                PersonalDataViewModelFactory(userPreference))[PersonalDataViewModel::class.java]

            viewModel.getUserProfileById(userId)

            viewModel.userProfileById.observe(viewLifecycleOwner, Observer {userProfile ->
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

        logout()

        //change language
        binding.tvBahasa.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }


        //navigate to 'Data Pribadi'
        binding.tvUserPersonal.setOnClickListener {
            val intent = Intent(requireContext(), PersonalDataActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToWelcome() {
        val intent = Intent(requireContext(), WelcomeActivity::class.java)
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}