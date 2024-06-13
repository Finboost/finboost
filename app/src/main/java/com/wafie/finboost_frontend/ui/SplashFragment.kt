package com.wafie.finboost_frontend.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.wafie.finboost_frontend.MainActivity
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Handler().postDelayed({
            lifecycleScope.launch {
                if (onBoardingFinished()) {
                    val userPreference = UserPreference.getInstance(requireContext().dataStore)
                    val userSession = userPreference.getSession().first()
                    if (userSession.isLogin) {
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                    } else {
                        startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                    }
                    requireActivity().finish()
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
                }
            }
        }, 3000)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}
