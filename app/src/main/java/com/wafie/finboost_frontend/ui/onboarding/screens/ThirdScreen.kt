package com.wafie.finboost_frontend.ui.onboarding.screens
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafie.finboost_frontend.MainActivity
import com.wafie.finboost_frontend.ui.onboarding.OnboardActivity
import com.wafie.finboost_frontend.databinding.FragmentThirdScreenBinding
import com.wafie.finboost_frontend.ui.WelcomeActivity

class ThirdScreen : Fragment() {
    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =   FragmentThirdScreenBinding.inflate(inflater, container, false)

        binding.btGetStarted.setOnClickListener {
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            startActivity(intent)
            onBoardFinished()
        }
        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    private fun onBoardFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
            .edit()
        sharedPref.putBoolean("Finished", true)
        sharedPref.apply()

}