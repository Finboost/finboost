package com.wafie.finboost_frontend.ui.onboarding.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.databinding.FragmentFirstScreenBinding
import com.wafie.finboost_frontend.ui.WelcomeActivity

class FirstScreen : Fragment() {

    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =   FragmentFirstScreenBinding.inflate(inflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.ibNext.setOnClickListener {
            viewPager?.currentItem = 1
        }

        binding.btnSkip.setOnClickListener {
            onBoardFinished()
            Handler(Looper.getMainLooper()).post {
                val intent = Intent(requireContext(), WelcomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}